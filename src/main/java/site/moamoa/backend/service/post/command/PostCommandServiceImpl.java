package site.moamoa.backend.service.post.command;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.repository.post.PostRepository;
import site.moamoa.backend.service.member.query.MemberQueryService;
import site.moamoa.backend.web.dto.base.SimplePostDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;
    private final MemberQueryService memberQueryService;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String POST_VIEW_KEY_PREFIX = "postView:";
    private static final Long EXPIRATION_VIEW_RECORD = 24 * 60 * 60L;  // 1 Day

    @Override
    public void updatePostViewCount(Long memberId, Long postId) {
        String key = buildPostViewKey(memberId, postId);

        // 이미 조회한 경우 무시
        if (isNewViewRecord(memberId, postId)) {
            saveViewRecord(key);
            updatePostView(postId);
        }
    }

    // queryDSL 적용 후 searchPostsByKeyword()로 바꾸기
    @Override
    public List<SimplePostDTO> findByKeyword(Long memberId, String keyword) {
        try {
            redisTemplate.opsForZSet()
                    .add("member::" + memberId, keyword, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            log.info("searching time : " + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

            String town = memberQueryService.findMemberById(memberId).getTown();

            redisTemplate.opsForZSet().addIfAbsent("town::" + town, keyword,0);
            redisTemplate.opsForZSet().add("town::" + town, keyword, 1);
            //log.info("score : " + redisTemplate.opsForZSet().)
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        List<SimplePostDTO> simplePostDTOS = new ArrayList<>();
        List<Post> posts = postRepository.findByProductNameContaining(keyword);
        for(Post post : posts) {
            simplePostDTOS.add(PostConverter.toSimplePostDTO(post));
        }
        return simplePostDTOS;
    }

    private String buildPostViewKey(Long memberId, Long postId) {
        return POST_VIEW_KEY_PREFIX + memberId + ":" + postId;
    }

    private boolean isNewViewRecord(Long memberId, Long postId) {
        return Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(buildPostViewKey(memberId, postId), postId));
    }

    private void saveViewRecord(String key) {
        redisTemplate.opsForSet().add(key, true);
        redisTemplate.expire(key, Duration.ofSeconds(EXPIRATION_VIEW_RECORD));
    }

    private void updatePostView(Long postId) {
        if (postRepository.updateView(postId) != 1) {
            throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        }
    }


}

package site.moamoa.backend.service.post.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.repository.post.PostRepository;
import site.moamoa.backend.service.member.query.MemberQueryService;
import site.moamoa.backend.web.dto.base.SimplePostDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;
    private final MemberQueryService memberQueryService;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String MEMBER_RECENT_KEYWORD_KEY_PREFIX = "member::";

    // queryDSL 적용 후 searchPostsByKeyword()로 바꾸기
    @Override
    public List<SimplePostDTO> findByKeyword(Long memberId, String keyword) {
        try {
            redisTemplate.opsForZSet()
                    .add("member::" + memberId, keyword, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            log.info("searching time : " + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

            String town = memberQueryService.findMemberById(memberId).getTown();
            redisTemplate.opsForZSet().addIfAbsent("town::" + town, keyword,1);
            redisTemplate.opsForZSet().incrementScore("town::" + town, keyword,1);
            //redisTemplate.opsForZSet().add("town::" + town, keyword, 1);
            
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

    @Override
    public PostResponseDTO.GetPosts findPostsByNear(Long memberId) {
        Address baseAddress = memberQueryService.findMemberById(memberId).getAddress();
        List<Post> posts = postRepository.findAllByNear(baseAddress.getLatitude(), baseAddress.getLongitude());
        return PostConverter.toGetPosts(
                posts.stream().map(PostConverter::toSimplePostDTO).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByLatest() {
        List<Post> posts = postRepository.findAllByRecent();
        return PostConverter.toGetPosts(
                posts.stream().map(PostConverter::toSimplePostDTO).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByRanking(Long memberId) {
        Member member = memberQueryService.findMemberById(memberId);
        List<Post> posts = postRepository.findAllByRanking(member.getTown());
        return PostConverter.toGetPosts(
                posts.stream().map(PostConverter::toSimplePostDTO).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByRecentKeyword(Long memberId) {
        Set<Object> range = redisTemplate.opsForZSet()
                .range(MEMBER_RECENT_KEYWORD_KEY_PREFIX + memberId, 0, 0);
        String keyword = range != null && !range.isEmpty() ? (String) range.iterator().next() : null;
        List<Post> posts = postRepository.findAllByKeyword(keyword);
        return PostConverter.toGetPosts(
                posts.stream().map(PostConverter::toSimplePostDTO).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPost findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
        return PostConverter.toGetPost(PostConverter.toPostDTO(post));
    }


}

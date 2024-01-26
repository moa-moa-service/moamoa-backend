package site.moamoa.backend.service.post.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.config.redis.RedisKey;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.repository.post.PostRepository;
import site.moamoa.backend.service.member.query.MemberQueryService;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;
    private final MemberQueryService memberQueryService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public PostResponseDTO.GetPosts findPostsByNear(Long memberId) {
        Address baseAddress = memberQueryService.findMemberById(memberId).getAddress();
        List<Post> posts = postRepository.findAllByNear(baseAddress.getLatitude(), baseAddress.getLongitude());
        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByLatest() {
        List<Post> posts = postRepository.findAllByRecent();
        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByRanking(Long memberId) {
        Member member = memberQueryService.findMemberById(memberId);
        List<Post> posts = postRepository.findAllByRanking(member.getTown());
        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByRecentKeyword(Long memberId) {
        Set<Object> range = redisTemplate.opsForZSet()
                .range(RedisKey.MEMBER_KEYWORD_KEY_PREFIX + memberId, 0, 0);
        String keyword = range != null && !range.isEmpty() ? (String) range.iterator().next() : null;
        List<Post> posts = postRepository.findAllByKeyword(keyword);
        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByConditions(String keyword, String category, Integer dDay, Integer total, Integer minPrice, Integer maxPrice) {
        List<Post> posts = postRepository.findAllByKeywordAndCondition(keyword, category, dDay, total, minPrice, maxPrice);
        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPost findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
        return PostConverter.toGetPost(PostConverter.toPostDTO(post, post.getPostImages(), post.getCategory()));
    }


}

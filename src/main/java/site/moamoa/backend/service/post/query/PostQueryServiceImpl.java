package site.moamoa.backend.service.post.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.repository.PostRepository;
import site.moamoa.backend.service.member.query.MemberQueryService;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;
    private final MemberQueryService memberQueryService;

    @Override
    public PostResponseDTO.GetPosts findPostsByNear(Long memberId) {
        Member member = memberQueryService.findMemberById(memberId);
        List<Post> posts = postRepository.findAllByNear(member.getTown());
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
    public PostResponseDTO.GetPost findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_FOUND));
        return PostConverter.toGetPost(PostConverter.toPostDTO(post));
    }
}

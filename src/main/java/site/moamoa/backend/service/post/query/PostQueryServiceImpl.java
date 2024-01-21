package site.moamoa.backend.service.post.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public PostResponseDTO.GetPosts findPosts(Long id) {

        Member member = memberQueryService.findMemberById(id);
        List<Post> posts = postRepository.findByDealTown(member.getTown());
        return PostConverter.toGetPosts(
                posts.stream()
                        .map(PostConverter::toSimplePostDTO)
                        .toList()
        );
    }
}

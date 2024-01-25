package site.moamoa.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.exception.MemberIsNotAuthorException;
import site.moamoa.backend.exception.MemberPostNotFoundException;
import site.moamoa.backend.repository.MemberPostRepository;

@Service
@AllArgsConstructor
public class MemberPostQueryServiceImpl implements MemberPostQueryService {
    private final MemberPostRepository memberPostRepository;

    @Override
    public void checkAuthor(Long memberId, Long postId) {
        MemberPost memberPost = memberPostRepository.findByMemberIdAndPostId(memberId, postId)
            .orElseThrow(
                () -> new MemberPostNotFoundException()
            );
        if (!(memberPost.getIsAuthorStatus() == IsAuthorStatus.AUTHOR))
            new MemberIsNotAuthorException();
    }
}

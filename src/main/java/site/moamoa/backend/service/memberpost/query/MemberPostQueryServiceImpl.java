package site.moamoa.backend.service.memberpost.query;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.api_payload.exception.handler.MemberPostHandler;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.repository.mapping.MemberPostRepository;

@Service
@AllArgsConstructor
public class MemberPostQueryServiceImpl implements MemberPostQueryService {
    private final MemberPostRepository memberPostRepository;

    @Override
    public void checkAuthor(Long memberId, Long postId) {
        MemberPost memberPost = memberPostRepository.findByMemberIdAndPostId(memberId, postId)
            .orElseThrow(
                () -> new MemberPostHandler(ErrorStatus.MEMBER_POST_NOT_FOUND)
            );
        if (memberPost.getIsAuthorStatus() != IsAuthorStatus.AUTHOR)
            new MemberHandler(ErrorStatus.MEMBER_IS_NOT_AUTHOR);
    }
}

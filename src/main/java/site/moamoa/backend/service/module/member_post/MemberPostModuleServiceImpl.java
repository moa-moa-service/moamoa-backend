package site.moamoa.backend.service.module.member_post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.api_payload.exception.handler.MemberPostHandler;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.repository.mapping.MemberPostRepository;

@Service
@RequiredArgsConstructor
public class MemberPostModuleServiceImpl implements MemberPostModuleService{

    private final MemberPostRepository memberPostRepository;

    @Override
    public MemberPost saveMemberPost(MemberPost memberPost) {
        return memberPostRepository.save(memberPost);
    }

    @Override
    public void validMemberPostIsAuthor(Long memberId, Long postId) {
        MemberPost memberPost = memberPostRepository.findByMemberIdAndPostId(memberId, postId)
                .orElseThrow(() -> new MemberPostHandler(ErrorStatus.MEMBER_POST_NOT_FOUND));
        if (memberPost.getIsAuthorStatus() != IsAuthorStatus.AUTHOR){
            throw new MemberHandler(ErrorStatus.MEMBER_IS_NOT_AUTHOR);
        }
    }
}

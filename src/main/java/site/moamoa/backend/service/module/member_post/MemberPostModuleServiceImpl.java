package site.moamoa.backend.service.module.member_post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.api_payload.exception.handler.MemberPostHandler;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.repository.mapping.member_post.MemberPostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPostModuleServiceImpl implements MemberPostModuleService{

    private final MemberPostRepository memberPostRepository;

    @Override
    public void saveMemberPost(MemberPost memberPost) {
        memberPostRepository.save(memberPost);
    }

    @Override
    public void validMemberPostIsAuthor(Long memberId, Long postId) {
        MemberPost memberPost = memberPostRepository.findByMemberIdAndPostId(memberId, postId)
                .orElseThrow(() -> new MemberPostHandler(ErrorStatus.MEMBER_POST_NOT_FOUND));
        if (memberPost.getIsAuthorStatus() != IsAuthorStatus.AUTHOR){
            throw new MemberHandler(ErrorStatus.MEMBER_IS_NOT_AUTHOR);
        }
    }

    @Override
    public Member findMemberPostByPostIdAndIsAuthor(Long postId) {
        return memberPostRepository.findPostAdminByPostId(postId);
    }

    @Override
    public List<Post> findPostsByRecruitingAndParticipating(Long memberId, IsAuthorStatus isAuthorStatus, CapacityStatus capacityStatus) {
        return memberPostRepository.findPostsByRecruitingAndParticipating(memberId, isAuthorStatus, capacityStatus);
    }
}

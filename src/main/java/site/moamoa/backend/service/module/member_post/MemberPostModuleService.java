package site.moamoa.backend.service.module.member_post;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;

import java.util.List;

public interface MemberPostModuleService {
    void saveMemberPost(MemberPost memberPost);

    void validMemberPostIsAuthor(Long memberId, Long postId);

    Member findMemberPostByPostIdAndIsAuthor(Long postId);

    List<Post> findPostsByRecruitingAndParticipating(Long memberId, IsAuthorStatus isAuthorStatus, CapacityStatus capacityStatus);
}
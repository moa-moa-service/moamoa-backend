package site.moamoa.backend.repository.mapping.member_post;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;

import java.util.List;

public interface MemberPostQueryDSLRepository {
    List<Post> findPostsByRecruitingAndParticipating(Long memberId, IsAuthorStatus isAuthorStatus, CapacityStatus capacityStatus);

    Member findPostAdminByPostId(Long postId);

    MemberPost findMemberPostAdminByPostId(Long postId);

    List<Member> findParticipatingMembersByPostId(Long postId);

  List<Member> findMembersByPostIdExcludingMember(Long postId, Long memberId);
}

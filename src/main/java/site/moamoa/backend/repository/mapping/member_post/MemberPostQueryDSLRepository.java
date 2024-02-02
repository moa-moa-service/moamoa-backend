package site.moamoa.backend.repository.mapping.member_post;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;

import java.util.List;

public interface MemberPostQueryDSLRepository {
    Member findPostAdminByPostId(Long postId);
    List<Post> findPostsByRecruitingAndParticipating(Long memberId, IsAuthorStatus isAuthorStatus, CapacityStatus capacityStatus);
}

package site.moamoa.backend.repository.mapping.member_post;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;

import java.util.List;

public interface MemberPostQueryDSLRepository {
    Member findPostAdminByPostId(Long postId);

    List<Post> findPostsByParticipating(Long memberId, CapacityStatus capacityStatus);

    List<Post> findPostsByRecruiting(Long memberId, CapacityStatus capacityStatus);
}

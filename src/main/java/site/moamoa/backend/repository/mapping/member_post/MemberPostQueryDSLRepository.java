package site.moamoa.backend.repository.mapping.member_post;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;

public interface MemberPostQueryDSLRepository {
    List<Post> findPostsByRecruitingAndParticipating(Long memberId, IsAuthorStatus isAuthorStatus, CapacityStatus capacityStatus);

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);
  
    Member findPostAdminByPostId(Long postId);
  
    PostResponseDTO.GetPost fetchDetailedPostByPostId(Long memberId, Long postId);
}

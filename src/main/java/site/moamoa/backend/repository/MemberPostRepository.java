package site.moamoa.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.mapping.MemberPost;

import java.util.List;

public interface MemberPostRepository extends JpaRepository<MemberPost, Long> {
    @Query("SELECT mp.post FROM MemberPost mp WHERE mp.member.id = :memberId AND mp.isAuthorStatus = 'PARTICIPATOR' AND mp.post.capacityStatus = :capacityStatus")
    List<Post> findParticipatedMember(@Param("memberId") Long memberId, @Param("capacityStatus") CapacityStatus capacityStatus);

    @Query("SELECT mp.post FROM MemberPost mp WHERE mp.member.id = :memberId AND mp.isAuthorStatus = 'Author' AND mp.post.capacityStatus = :capacityStatus")
    List<Post> findRecruitingMember(@Param("memberId") Long memberId, @Param("capacityStatus") CapacityStatus capacityStatus);
}

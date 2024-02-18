package site.moamoa.backend.repository.mapping.member_post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.mapping.MemberPost;

import java.util.List;
import java.util.Optional;

public interface MemberPostRepository extends JpaRepository<MemberPost, Long>, MemberPostQueryDSLRepository {
    Optional<MemberPost> findByMemberIdAndPostId(Long memberId, Long postId);

    Boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    List<MemberPost> findMemberPostsByMemberId(Long memberId);

    @Modifying
    @Query("UPDATE MemberPost mp SET mp.member = null WHERE mp.member.id IN :ids")
    void nullifyMemberInMemberPostsByMemberIds(@Param("ids") List<Long> memberIds);
}

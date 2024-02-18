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

    @Modifying
    @Query("DELETE FROM MemberPost mp WHERE mp.member.id IN :memberIds OR mp.post.id IN :postIds")
    void deleteMemberPostsByMemberIdsOrPostIds(@Param("memberIds") List<Long> memberIds, @Param("postIds") List<Long> postIds);
}

package site.moamoa.backend.repository.mapping.member_post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.mapping.MemberPost;

import java.util.List;

public interface MemberPostRepository extends JpaRepository<MemberPost, Long>, MemberPostQueryDSLRepository {
    Optional<MemberPost> findByMemberIdAndPostId(Long memberId, Long postId);
}

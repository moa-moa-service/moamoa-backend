package site.moamoa.backend.repository.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.mapping.MemberPost;

import java.util.Optional;

public interface MemberPostRepository extends JpaRepository<MemberPost, Long>, MemberPostQueryDSLRepository {
    Optional<MemberPost> findByMemberIdAndPostId(Long memberId, Long postId);
}

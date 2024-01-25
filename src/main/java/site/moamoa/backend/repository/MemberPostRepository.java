package site.moamoa.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.mapping.MemberPost;

public interface MemberPostRepository extends JpaRepository<MemberPost, Long> {

  Optional<MemberPost> findByMemberIdAndPostId(Long memberId, Long postId);
}

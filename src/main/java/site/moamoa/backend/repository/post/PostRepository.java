package site.moamoa.backend.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Transactional
  @Modifying
  @Query("UPDATE Post mm SET mm.capacityStatus = :afterStatus WHERE mm.id = :postId AND mm.capacityStatus = :previousStatus")
  void updateStatusToFull(Long postId, CapacityStatus previousStatus, CapacityStatus afterStatus);
}

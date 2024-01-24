package site.moamoa.backend.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.mapping.MemberPost;

public interface MemberPostRepository extends JpaRepository<MemberPost, Long> {

}

package site.moamoa.backend.repository.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}

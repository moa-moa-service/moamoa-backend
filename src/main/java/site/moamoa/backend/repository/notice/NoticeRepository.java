package site.moamoa.backend.repository.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.Notice;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Modifying
    @Query("DELETE FROM Notice n WHERE n.post.id IN :postIds")
    void nullifyPostInNotices(@Param("postIds") List<Long> postIds);
}

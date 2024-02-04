package site.moamoa.backend.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

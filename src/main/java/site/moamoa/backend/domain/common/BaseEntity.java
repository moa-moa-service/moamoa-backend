package site.moamoa.backend.domain.common;

import jakarta.persistence.EntityListeners;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> 08337f3 (:bug: Fix:게시글 수정 DTO에서 상품명 필드 추가)
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

<<<<<<< HEAD
import java.time.LocalDateTime;

=======
>>>>>>> 08337f3 (:bug: Fix:게시글 수정 DTO에서 상품명 필드 추가)
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
<<<<<<< HEAD
}
=======
}
>>>>>>> 08337f3 (:bug: Fix:게시글 수정 DTO에서 상품명 필드 추가)

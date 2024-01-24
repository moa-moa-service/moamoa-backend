package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.enums.CapacityStatus;

@Builder
public record SimplePostDTO(
        Long postId,
        String image,
        String productName,
        Integer personnel,
        Integer dDay,
        Integer price,
        CapacityStatus status
) {
}

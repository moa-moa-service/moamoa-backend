package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import site.moamoa.backend.domain.enums.CapacityStatus;

@Builder
public record SimplePostDTO(
        String imageUrl,
        String productName,
        Integer personnel,
        Integer dDay,
        Integer price,
        CapacityStatus status
) {
}

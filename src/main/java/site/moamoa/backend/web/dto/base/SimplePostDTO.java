package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import site.moamoa.backend.domain.enums.CapacityStatus;

@Builder
public record SimplePostDTO(
        Long postId,
        String imageUrl,
        String productName,
        String dealTown,
        Integer viewCount,
        Integer personnel,
        Integer dDay,
        Integer price,
        CapacityStatus status
) {
}

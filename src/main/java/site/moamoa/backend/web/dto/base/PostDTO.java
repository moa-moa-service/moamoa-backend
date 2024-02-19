package site.moamoa.backend.web.dto.base;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.domain.enums.CapacityStatus;

@Builder
public record PostDTO(
        Long postId,
        String category,
        Integer personnel,
        Integer available,
        LocalDateTime deadline,
        String productName,
        List<String> imageUrl,
        Address dealLocation,
        String dealTown,
        CapacityStatus capacityStatus,
        String town,
        Integer price,
        String description,
        Integer viewCount
) {
}

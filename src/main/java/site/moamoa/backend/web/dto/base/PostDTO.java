package site.moamoa.backend.web.dto.base;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import site.moamoa.backend.domain.embedded.Address;

@Builder
public record PostDTO(
        String category,
        Integer personnel,
        Integer available,
        LocalDateTime deadline,
        String productName,
        List<String> imageUrl,
        Address dealLocation,
        Integer price,
        String description,
        Integer viewCount
) {
}

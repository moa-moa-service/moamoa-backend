package site.moamoa.backend.web.dto.base;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.embedded.Address;

@Builder
public record PostDTO(
        Long categoryId,
        Integer personnel,
        LocalDate deadline,
        String productName,
        List<String> imageUrl,
        Address dealLocation,
        Integer price,
        String description
) {
}

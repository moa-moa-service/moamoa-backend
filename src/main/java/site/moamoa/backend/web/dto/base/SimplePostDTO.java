package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@RequiredArgsConstructor
public class SimplePostDTO {
    MultipartFile image;
    String product_name;
    Integer personnel;
    Integer d_day;
    Integer price;
    CapacityStatus status;
}

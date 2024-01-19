package site.moamoa.backend.web.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.embedded.Address;

public class PostRequestDTO {

    public record AddPost(
            @NotNull
            Long categoryId,
            @NotNull
            @Min(value = 1, message = "최소 1명 이상이어야 합니다.")
            @Max(value = 10, message = "최대 10명까지만 허용됩니다.")
            Integer personnel,
            @NotNull
            @Future(message = "현재 날짜 이후의 날짜여야 합니다.")
            LocalDate deadline,
            @NotNull
            String productName,
            @Size(max = 10, message = "이미지는 최대 10개까지만 허용됩니다.")
            List<MultipartFile> image,
            @NotNull
            Address dealLocation,
            @NotNull
            Integer price,
            String description
    ) {
    }

    public record UpdatePostInfo(
            Long categoryId,
            @Min(value = 1, message = "최소 1명 이상이어야 합니다.")
            @Max(value = 10, message = "최대 10명까지만 허용됩니다.")
            Integer personnel,
            @Future(message = "현재 날짜 이후의 날짜여야 합니다.")
            LocalDate deadline,
            @Size(max = 10, message = "이미지는 최대 10개까지만 허용됩니다.")
            List<MultipartFile> image,
            Address dealLocation,
            String productName,
            Integer price,
            String description
    ) {
    }
}

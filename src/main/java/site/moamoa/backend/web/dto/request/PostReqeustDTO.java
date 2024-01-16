package site.moamoa.backend.web.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.web.multipart.MultipartFile;

public class PostReqeustDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class AddPost {
        @NotNull
        Long category_id;
        @NotNull
        @Min(value = 1, message = "최소 1명 이상이어야 합니다.")
        @Max(value = 10, message = "최대 10명까지만 허용됩니다.")
        Integer personnel;
        @NotNull
        @Future(message = "현재 날짜 이후의 날짜여야 합니다.")
        Date deadline;
        @NotNull
        String product_name;
        @Size(max = 10, message = "이미지는 최대 10개까지만 허용됩니다.")
        List<MultipartFile> image;
        @NotNull
        Address deal_location;
        @NotNull
        Integer price;
        String description;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class UpdatePostInfo {
        Long category_id;
        @Min(value = 1, message = "최소 1명 이상이어야 합니다.")
        @Max(value = 10, message = "최대 10명까지만 허용됩니다.")
        Integer personnel;
        @Future(message = "현재 날짜 이후의 날짜여야 합니다.")
        Date deadline;
        @Size(max = 10, message = "이미지는 최대 10개까지만 허용됩니다.")
        List<MultipartFile> image;
        Address deal_location;
        Integer price;
        String description;
    }
}

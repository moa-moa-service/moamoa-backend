package site.moamoa.backend.web.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

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
            LocalDateTime deadline,
            @NotNull
            String productName,
            @NotNull
            Address dealLocation,
            @NotNull
            String dealTown,
            @NotNull
            String town,
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
            LocalDateTime deadline,
            Address dealLocation,
            String productName,
            String dealTown,
            String town,
            Integer price,
            String description
    ) {
    }

    public record RegisterPost(
            @NotNull
            Integer amount
    ) {
    }
}

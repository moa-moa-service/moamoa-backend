package site.moamoa.backend.api_payload.code;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ReasonDTO(
        HttpStatus httpStatus,
        boolean isSuccess,
        String code,
        String message
) {
}

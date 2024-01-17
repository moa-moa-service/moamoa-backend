package site.moamoa.backend.api_payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import site.moamoa.backend.api_payload.code.BaseCode;
import site.moamoa.backend.api_payload.code.status.SuccessStatus;

@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public record ApiResponseDTO<T>(
        @JsonProperty("isSuccess")
        Boolean isSuccess,
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T result
) {

    //Success
    public static <T> ApiResponseDTO<T> onSuccess(T result) {
        return new ApiResponseDTO<>(
                true,
                SuccessStatus._OK.getCode(),
                SuccessStatus._OK.getMessage(),
                result
        );
    }

    public static <T> ApiResponseDTO<T> of(BaseCode code, T result) {
        return new ApiResponseDTO<>(
                true,
                code.getReasonHttpStatus().code(),
                code.getReasonHttpStatus().message(),
                result
        );
    }

    //Fail
    public static <T> ApiResponseDTO<T> onFailure(String code, String message, T data) {
        return new ApiResponseDTO<>(
                true,
                code,
                message,
                data
        );
    }
}

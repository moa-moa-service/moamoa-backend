package site.moamoa.backend.api_payload.code;

public interface BaseErrorCode {
    ErrorReasonDTO getReason();

    ErrorReasonDTO getReasonHttpStatus();
}

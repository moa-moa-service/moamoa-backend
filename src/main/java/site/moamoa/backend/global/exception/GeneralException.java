package site.moamoa.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.moamoa.backend.api_payload.code.BaseErrorCode;
import site.moamoa.backend.api_payload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
    return this.code.getReason();
  }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
    return this.code.getReasonHttpStatus();
  }
}

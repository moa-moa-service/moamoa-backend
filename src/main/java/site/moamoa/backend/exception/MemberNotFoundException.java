package site.moamoa.backend.exception;

import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.global.exception.GeneralException;

public class MemberNotFoundException extends GeneralException {
  public MemberNotFoundException() {
    super(ErrorStatus.MEMBER_NOT_FOUND);
  }

}

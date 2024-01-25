package site.moamoa.backend.web.exception;

import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.global.exception.GeneralException;

public class PostNotFoundException extends GeneralException {
  public PostNotFoundException() {
    super(ErrorStatus.POST_NOT_FOUND);
  }
}

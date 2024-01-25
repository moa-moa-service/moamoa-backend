package site.moamoa.backend.web.exception;

import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.global.exception.GeneralException;

public class MemberPostNotFoundException extends GeneralException {

    public MemberPostNotFoundException() {
        super(ErrorStatus.Member_POST_NOT_FOUND);
    }
}

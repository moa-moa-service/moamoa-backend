package site.moamoa.backend.web.exception;

import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.global.exception.GeneralException;

public class MemberIsNotAuthorException extends GeneralException {

    public MemberIsNotAuthorException() {
        super(ErrorStatus.MEMBER_IS_NOT_AUTHOR);
    }

}

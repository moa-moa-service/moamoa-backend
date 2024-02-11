package site.moamoa.backend.api_payload.exception.handler;

import site.moamoa.backend.api_payload.code.BaseErrorCode;
import site.moamoa.backend.api_payload.exception.GeneralException;

public class MemberPostHandler extends GeneralException {
    public MemberPostHandler(BaseErrorCode code) {super(code);}

}

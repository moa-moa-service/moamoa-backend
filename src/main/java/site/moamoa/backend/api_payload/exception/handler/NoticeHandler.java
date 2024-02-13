package site.moamoa.backend.api_payload.exception.handler;

import site.moamoa.backend.api_payload.code.BaseErrorCode;
import site.moamoa.backend.api_payload.exception.GeneralException;

public class NoticeHandler extends GeneralException {
    public NoticeHandler(BaseErrorCode code) {super(code);}
}

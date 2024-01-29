package site.moamoa.backend.api_payload.exception.handler;

import site.moamoa.backend.api_payload.code.BaseErrorCode;
import site.moamoa.backend.global.exception.GeneralException;

public class KeywordHandler extends GeneralException {
    public KeywordHandler(BaseErrorCode code) {
        super(code);
    }
}

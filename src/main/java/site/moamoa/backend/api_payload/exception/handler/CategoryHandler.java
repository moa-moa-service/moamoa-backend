package site.moamoa.backend.api_payload.exception.handler;

import site.moamoa.backend.api_payload.code.BaseErrorCode;
import site.moamoa.backend.global.exception.GeneralException;

public class CategoryHandler extends GeneralException {
    public CategoryHandler(BaseErrorCode code) {super(code);}
}

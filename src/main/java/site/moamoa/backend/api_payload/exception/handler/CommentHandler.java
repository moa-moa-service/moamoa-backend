package site.moamoa.backend.api_payload.exception.handler;

import site.moamoa.backend.api_payload.code.BaseErrorCode;
import site.moamoa.backend.api_payload.exception.GeneralException;

public class CommentHandler extends GeneralException {
    public CommentHandler(BaseErrorCode code) {
        super(code);
    }
}
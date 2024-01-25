package site.moamoa.backend.exception;

import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.global.exception.GeneralException;

public class CategoryNotFoundException extends GeneralException{
    public CategoryNotFoundException() {
        super(ErrorStatus.CATEGORY_NOT_FOUND);
    }
}

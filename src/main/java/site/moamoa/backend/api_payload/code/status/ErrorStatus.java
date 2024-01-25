package site.moamoa.backend.api_payload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.moamoa.backend.api_payload.code.BaseErrorCode;
import site.moamoa.backend.api_payload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "찾을 수 없습니다."),

    // 카테고리 에러
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST,"CATEGORY401","카테고리가 없습니다."),

    // 멤버 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST,"MEMBER401","멤버가 없습니다."),

    // 게시물 에러
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST,"POST401","게시물이 없습니다."),

    // 멤버-게시물 에러
    Member_POST_NOT_FOUND(HttpStatus.BAD_REQUEST,"MEMBER_POST401","공동구매 참여 기록이 없습니다."),
    MEMBER_IS_NOT_AUTHOR(HttpStatus.BAD_REQUEST,"MEMBER_POST402","해당 멤버는 글쓴이가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .message(message)
                .code(code)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return  ErrorReasonDTO.builder()
                .isSuccess(false)
                .message(message)
                .code(code)
                .httpStatus(httpStatus)
                .build();
    }

}

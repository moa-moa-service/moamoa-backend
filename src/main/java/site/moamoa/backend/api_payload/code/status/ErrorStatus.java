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

    // Category
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "CATEGORY404", "해당 카테고리를 찾을 수 없습니다."),

    // Member-Post
    MEMBER_IS_NOT_AUTHOR(HttpStatus.FORBIDDEN, "MEMBER_POST403", "해당 멤버에게는 금지된 요청입니다."),
    MEMBER_POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_POST404", "공동구매 참여 기록이 없습니다."),
    MEMBER_CAN_NOT_BE_PARTICIPATOR(HttpStatus.FORBIDDEN, "MEMBER_POST4003", "해당 멤버는 참여자가 될 수 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "해당 사용자를 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER4001", "이미 추가 정보가 등록된 멤버입니다."),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404", "해당 공동구매 게시글을 찾을 수 없습니다."),

    // Keyword
    KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "KEYWORD404", "해당 검색어를 찾을 수 없습니다."),

    // Notice
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE404","해당 공지사항을 찾을 수 없습니다."),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404", "해당 댓글을 찾을 수 없습니다.")

    ;

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
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .message(message)
                .code(code)
                .httpStatus(httpStatus)
                .build();
    }
}
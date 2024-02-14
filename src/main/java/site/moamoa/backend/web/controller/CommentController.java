package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.service.component.command.comment.CommentCommandService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.CommentRequestDTO;
import site.moamoa.backend.web.dto.response.CommentResponseDTO;

@Tag(name = "공지사항 댓글 API", description = "공지사항 댓글 관련 API")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentCommandService commentCommandService;

    @PostMapping("/api/notices/{noticeId}/comments")
    @Operation(
            summary = "공동구매 공지사항 댓글 등록",
            description = "댓글에 대한 정보를 받아 공동구매 공지사항에 댓글을 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
    })
    public ApiResponseDTO<CommentResponseDTO.AddCommentResult> registerComments(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable(name = "noticeId") Long noticeId,
            @RequestBody CommentRequestDTO.AddComment request
    ) {
        CommentResponseDTO.AddCommentResult resultDTO = commentCommandService.registerComment(auth.id(), noticeId, request);
        return ApiResponseDTO.onSuccess(resultDTO);
    }
}
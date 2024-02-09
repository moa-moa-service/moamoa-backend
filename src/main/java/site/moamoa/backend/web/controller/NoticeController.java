package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.service.component.command.notice.NoticeCommandService;
import site.moamoa.backend.service.component.query.notice.NoticeQueryService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO;

@Tag(name = "공동구매 공지사항 API", description = "공동구매 공지사항 페이지 관련 API")
@RequiredArgsConstructor
@RestController
public class NoticeController {

    private final NoticeQueryService noticeQueryService;
    private final NoticeCommandService noticeCommandService;

    @PostMapping("/api/posts/{postId}/notices")
    @Operation(
            summary = "공동구매 공지사항 등록 (개발중)",
            description = "공지사항에 대한 정보를 받아 공동구매 게시글에 공지사항을 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
    })
    public ApiResponseDTO<NoticeResponseDTO.AddNoticeResult> registerNotices(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable Long postId,
            @RequestBody NoticeRequestDTO.AddNotice request,
            @RequestPart("file") MultipartFile image
    ) {
        NoticeResponseDTO.AddNoticeResult resultDTO = noticeCommandService.registerNotice(auth.id(), postId, request, image);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/notices/{noticeId}")
    @Operation(
            summary = "공동구매 공지사항 상세 조회 (개발중)",
            description = "공지사항 ID를 받아 공지사항을 상세 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
    })
    public ApiResponseDTO<NoticeResponseDTO.GetNotice> getNoticeByNoticeId(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable Long noticeId
    ) {
        NoticeResponseDTO.GetNotice resultDTO = noticeQueryService.findNoticeById(noticeId);
        return ApiResponseDTO.onSuccess(resultDTO);
    }
}

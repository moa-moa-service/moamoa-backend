package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.service.component.query.notification.NotificationQueryService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.response.NotificationResponseDTO;

import java.util.List;

@Tag(name = "알림 API", description = "알림 페이지 관련 API (개발중)")
@RequiredArgsConstructor
@RestController
public class NotificationController {
    private final NotificationQueryService notificationQueryService;
    @GetMapping("/api/notifications")
    @Operation(
            summary = "알림 조회",
            description = "현재 접속 중인 멤버의 알림을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
    })
    public ApiResponseDTO<NotificationResponseDTO.GetNotifications> getNotificationByMemberId(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        NotificationResponseDTO.GetNotifications notificationList = notificationQueryService.findNotificationByMemberId(auth.id());
        return ApiResponseDTO.onSuccess(notificationList);
    }
}

package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.global.oauth2.service.CustomOAuth2UserService;
import site.moamoa.backend.web.dto.AuthInfoDTO;
import site.moamoa.backend.web.dto.MemberRequestDTO;

@Tag(name = "인증 API", description = "보안 인증 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final CustomOAuth2UserService oAuth2UserService;


    @PostMapping("/api/auth/member-info")
    @Operation(
            summary = "사용자 추가 정보 등록",
            description = "사용자의 추가 정보를 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public String exceptionAPI(
            @AuthenticationPrincipal AuthInfoDTO auth, // security context에서 가져온 user임. member entity랑 다름
            @RequestBody MemberRequestDTO.AddMemberInfo memberInfo
    ) {
        Member member = oAuth2UserService.addMemberInfo(auth.id(), memberInfo);
        log.info("AuthController member : {}", member);
        return "ok"; // todo 추후 응답 response에 맞게 반환
    }

    @Hidden
    @GetMapping("/api/auth/after")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("test authentication : {}", authentication);
        log.info("[test authentication getPrincipal: {}", authentication.getPrincipal());
        return "ok";
    }
}

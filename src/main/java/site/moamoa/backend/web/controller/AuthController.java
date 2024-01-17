package site.moamoa.backend.web.controller;

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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final CustomOAuth2UserService oAuth2UserService;

    @PostMapping("/member-info")
    public String exceptionAPI(
            @AuthenticationPrincipal AuthInfoDTO auth, // security context에서 가져온 user임. member entity랑 다름
            @RequestBody MemberRequestDTO.AddMemberInfo memberInfo
    ) {
        Member member = oAuth2UserService.addMemberInfo(auth.id(), memberInfo);
        log.info("AuthController member : {}", member);
        return "ok"; // todo 추후 응답 response에 맞게 반환
    }

    @GetMapping("/after")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("test authentication : {}", authentication);
        log.info("[test authentication getPrincipal: {}", authentication.getPrincipal());
        return "ok";
    }
}

package site.moamoa.backend.global.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.SuccessStatus;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.enums.RoleType;
import site.moamoa.backend.global.jwt.service.JwtService;
import site.moamoa.backend.global.oauth2.CustomOAuth2User;
import site.moamoa.backend.repository.MemberRepository;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            String accessToken = jwtService.createAccessToken(oAuth2User.getId());
            String refreshToken = jwtService.createRefreshToken();
            memberSetRefreshToken(oAuth2User, refreshToken);
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

            // User의 Role이 GUEST일 경우 처음 요청한 회원
            if(oAuth2User.getRoleType() == RoleType.GUEST) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        } catch (Exception e) {
            throw e;
        }

    }
    // todo service 계층이 아닌 곳에서 저장을 해도 괜찮은가? -> jwtservice에서 저장을 하는 것이 옳은가?
    private void memberSetRefreshToken(CustomOAuth2User oAuth2User, String refreshToken) {
        Member member = memberRepository.findById(oAuth2User.getId()).orElseThrow(RuntimeException::new);
        member.addRefreshToken(refreshToken);
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getId());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getId(), refreshToken);
    }
}

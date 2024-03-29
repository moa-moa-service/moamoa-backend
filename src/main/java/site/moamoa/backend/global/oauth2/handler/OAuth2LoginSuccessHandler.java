package site.moamoa.backend.global.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import site.moamoa.backend.domain.enums.RoleType;
import site.moamoa.backend.global.jwt.service.JwtService;
import site.moamoa.backend.global.oauth2.CustomOAuth2User;
import site.moamoa.backend.service.component.command.member.MemberCommandService;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberCommandService memberCommandService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            String accessToken = jwtService.createAccessToken(oAuth2User.getId());
            String refreshToken = jwtService.createRefreshToken();
            memberCommandService.memberSetRefreshToken(oAuth2User, refreshToken);
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

            // User의 Role이 GUEST일 경우 처음 요청한 회원
            if(oAuth2User.getRoleType() == RoleType.GUEST) {
                response.sendRedirect("http://localhost:5173/member-info?authorization=" + accessToken);
            }

            // User의 Role이 Member의 경우 기존 회원
            if(oAuth2User.getRoleType() == RoleType.MEMBER) {
                response.sendRedirect("http://localhost:5173?authorization=" + accessToken);
            }
        } catch (Exception e) {
            throw e;
        }

    }
}

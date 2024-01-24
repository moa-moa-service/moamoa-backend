package site.moamoa.backend.global.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.enums.RoleType;
import site.moamoa.backend.global.jwt.service.JwtService;
import site.moamoa.backend.repository.member.MemberRepository;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * 사용자의 요청 헤더에 RefreshToken이 있는 경우 -> AccessToken이 만료됨을 의미
         * RefreshToken 존재하지 않거나 유효하지 않다면(DB에 저장된 RefreshToken과 다름) null을반환
         */
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        // 사용자가 AccessToken이 만료되어서 RefreshToken을 보냈다면 DB의 리프레시 토큰과 일치하는지 판단 후, AccessToken을 재발급
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return; // RefreshToken을 보낸 경우에는 AccessToken을 재발급 하고 인증 처리는 하지 않아야 하므로 return으로 필터 진행 막기
        }

        // AccessToken을 검사하고 인증을 처리하는 로직 수행
        if (refreshToken == null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }
    }

    /**
     * 헤더에서 추출한 리프레시 토큰으로 DB에서 해당 유저를 찾고, 해당 유저가 있다면 JwtService.createAccessToken()으로 AccessToken 생성,
     * reIssueRefreshToken()로 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트
     * 마지막으로 응답 헤더에 보냄
     */
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(member);
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(member.getId()),
                            reIssuedRefreshToken);
                });
    }

    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }

    /**
     * request에서 extractAccessToken()으로 액세스 토큰 추출 후, isTokenValid()로 유효한 토큰인지 검증
     * 유효한 토큰이면, 액세스 토큰에서 id 추출한 후 해당 id 유저 객체 반환
     * 그 유저 객체를 saveAuthentication()으로 인증 처리하여
     * 인증 허가 처리된 객체를 SecurityContextHolder에 저장
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractId(accessToken)
                        .ifPresent(id -> memberRepository.findById(id)
                                .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(Member myMember) {
        log.info("JwtAuthenticationProcessingFilter saveAuthentication 호출 확인");
        AuthInfoDTO authInfoDTO = AuthInfoDTO.builder()
                .id(myMember.getId())
                .username(myMember.getNickname())
                .authorities(
                        Set.of(myMember.getRoleType()).stream()
                                .map(RoleType::getKey)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toUnmodifiableSet()))
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(authInfoDTO, null,
                        authoritiesMapper.mapAuthorities(authInfoDTO.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

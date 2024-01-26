package site.moamoa.backend.global.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.global.oauth2.CustomOAuth2User;
import site.moamoa.backend.repository.member.MemberRepository;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
@Transactional(readOnly = true)
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    private static final String BEARER = "Bearer ";

    private final MemberRepository memberRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public String createAccessToken(Long id) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void expiredAccessToken(String accessToken) {
        Long expiration = getExpiration(accessToken);
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        log.info("[JwtService 재발급 Access Token : {}]", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("[JwtService Access, Refresh Token 헤더 설정 완료]");
    }

    /**
     * 헤더에서 RefreshToken 추출
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        log.info("[JwtService : extractAccessToken extractAccessToken : {}]", request.getHeader(accessHeader));
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * AccessToken에서 Email 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<Long> extractId(String accessToken) {
        log.info("JwtService : extractId 호출 후 토큰 유효성 검사 확인");
        String isLogout = redisTemplate.opsForValue().get(accessToken);
        if (isLogout == null || isLogout.isEmpty()) {
            try {
                // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
                return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                        .build() // 반환된 빌더로 JWT verifier 생성
                        .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                        .getClaim(ID_CLAIM)
                        .asLong()
                );
            } catch (Exception e) {
                log.error("액세스 토큰이 유효하지 않습니다.");
            }
        }

        // isLogout이 null 혹은 빈 문자열이 아니라면 블랙리스트에 등록되어 있는 토큰
        return Optional.empty();
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
    public void updateRefreshToken(Long id, String refreshToken) {
        memberRepository.findById(id)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token) {
        log.info("JwtService isTokenValid token : {}", token);
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    public Long getExpiration(String accessToken) {
        Date expiration = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(accessToken)
                .getExpiresAt();
        return expiration.getTime() - new Date().getTime();
    }

    @Transactional
    public void memberSetRefreshToken(CustomOAuth2User oAuth2User, String refreshToken) {
        log.info("JwtService memberSetRefreshToken : {}", refreshToken);
        Member member = memberRepository.findById(oAuth2User.getId()).orElseThrow(RuntimeException::new);
        member.addRefreshToken(refreshToken);
    }

    @Transactional
    public MemberResponseDTO.LogoutInfo memberDeleteRefreshToken(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        member.addRefreshToken(null);
        return MemberConverter.logoutMemberInfoResult(member);
    }
}

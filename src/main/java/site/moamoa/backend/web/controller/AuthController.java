package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.global.jwt.service.JwtService;
import site.moamoa.backend.global.oauth2.service.CustomOAuth2UserService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.MemberRequestDTO.AddMemberInfo;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO.AddMemberInfoResult;

@Tag(name = "인증 API", description = "보안 인증 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final CustomOAuth2UserService oAuth2UserService;
    private final JwtService jwtService;

    @PostMapping("/api/auth/member-info")
    @Operation(
            summary = "사용자 추가 정보 등록",
            description = "사용자의 추가 정보를 등록합니다. town = 동네, Address.name = 상세 주소"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<AddMemberInfoResult> addMemberInfoAPI(
            @AuthenticationPrincipal AuthInfoDTO auth, // security context에서 가져온 user임. member entity랑 다름
            @RequestBody AddMemberInfo request
    ) {
        AddMemberInfoResult resultDTO = oAuth2UserService.addMemberInfo(auth.id(), request);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PostMapping("/api/auth/logout")
    @Operation(
            summary = "로그아웃",
            description = "로그아웃 시 액세스 토큰을 만료시킵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<MemberResponseDTO.LogoutInfo> logoutAPI(
            @AuthenticationPrincipal AuthInfoDTO auth,
            HttpServletRequest request
    ) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(RuntimeException::new);
        jwtService.expiredAccessToken(accessToken);
        MemberResponseDTO.LogoutInfo resultDTO = jwtService.memberDeleteRefreshToken(auth.id());
        //todo jwtService, oauth2Service에 로직이 다들어가있는데 LoginService를 하나 새로 만들어서 로그인, 로그아웃 로직을 넣는 것이 좋아보임.
        return ApiResponseDTO.onSuccess(resultDTO);
    }

}

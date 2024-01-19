package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.moamoa.backend.api_payload.ApiResponseDTO;

@Tag(name = "Member API", description = "사용자 정보 관련 API")
@RequiredArgsConstructor
@RestController
public class MemberController {

    @GetMapping("/api/members")
    @Operation(
            summary = "사용자 정보 조회 (개발중)",
            description = "사용자의 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<?> getMemberByNickname(
            //TODO: Security 추가시 인증부 구현 필요
            @Parameter(description = "사용자 이름", example = "Luke")
            @RequestParam String nickname
    ) {
        //TODO: 서비스 로직 추가 필요
        return null;    //TODO: Result DTO 반환 필요
    }

    @PatchMapping("/api/members/image")
    @Operation(
            summary = "사용자 프로필 사진 수정 (개발중)",
            description = "사용자의 프로필 사진 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<?> updateMyImage(
            //TODO: Security 추가시 인증부 구현 필요
    ) {
        //TODO: 서비스 로직 추가 필요
        return null;    //TODO: Result DTO 반환 필요
    }

    @PatchMapping("/api/members/location")
    @Operation(
            summary = "사용자 동네 수정 (개발중)",
            description = "사용자의 동네 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<?> updateMyLocation(
            //TODO: Security 추가시 인증부 구현 필요
    ) {
        //TODO: 서비스 로직 추가 필요
        return null;    //TODO: Result DTO 반환 필요
    }

    @GetMapping("/api/members/post")
    @Operation(
            summary = "사용자의 공동구매 참여 목록 조회 (개발중)",
            description = "사용자의 공동구매 참여 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<?> getPostsByParticipating(
            //TODO: Security 추가시 인증부 구현 필요
            @Parameter(description = "모집 마감 여부(ONGOING, END)", example = "END")
            @RequestParam String status
    ) {
        //TODO: 서비스 로직 추가 필요
        return null;    //TODO: Result DTO 반환 필요
    }

}

package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.request.MemberRequestDTO.UpdateMemberAddress;
import site.moamoa.backend.web.dto.request.MemberRequestDTO.UpdateMemberImage;
import site.moamoa.backend.web.dto.response.MemberResponseDTO.GetMyInfoResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO.GetMyPostList;

import static site.moamoa.backend.web.dto.response.MemberResponseDTO.*;

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
    public ApiResponseDTO<GetMyInfoResult> getMemberByNickname(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @Parameter(description = "사용자 이름", example = "Luke")
            @RequestParam String nickname
    ) {
        GetMyInfoResult resultDTO = null;   //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PatchMapping("/api/members/image")
    @Operation(
            summary = "사용자 프로필 사진 수정 (개발중)",
            description = "사용자의 프로필 사진 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<UpdateMemberImageResult> updateMyImage(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @RequestBody UpdateMemberImage request
            ) {
        UpdateMemberImageResult resultDTO = null;  //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PatchMapping("/api/members/location")
    @Operation(
            summary = "사용자 동네 수정 (개발중)",
            description = "사용자의 동네 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<UpdateMemberAddressResult> updateMyLocation(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @RequestBody UpdateMemberAddress request
    ) {
        UpdateMemberAddressResult resultDTO = null; //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/members/post")
    @Operation(
            summary = "사용자의 공동구매 참여 목록 조회 (개발중)",
            description = "사용자의 공동구매 참여 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetMyPostList> getPostsByParticipating(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @Parameter(description = "모집 마감 여부(ONGOING, END)", example = "END")
            @RequestParam String status
    ) {
        GetMyPostList resultDTO = null;     //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

}

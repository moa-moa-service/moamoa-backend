package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.service.MemberCommandService;
import site.moamoa.backend.service.MemberQueryService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.base.MemberDTO;
import site.moamoa.backend.web.dto.request.MemberRequestDTO.UpdateMemberAddress;
import site.moamoa.backend.web.dto.request.MemberRequestDTO.UpdateMemberImage;
import site.moamoa.backend.web.dto.response.MemberResponseDTO.GetMyInfoResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.GetMyPostList;

import static site.moamoa.backend.web.dto.response.MemberResponseDTO.*;

@Tag(name = "사용자 API", description = "사용자 정보 관련 API")
@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @GetMapping("/api/members")
    @Operation(
            summary = "사용자 정보 조회",
            description = "사용자의 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetMyInfoResult> getMemberByNickname(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetMyInfoResult resultDTO = memberQueryService.getMyInfo(auth.id());
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PatchMapping(value = "/api/members/image", consumes = "multipart/form-data")
    @Operation(
            summary = "사용자 프로필 사진 저장 및 수정",
            description = "사용자의 프로필 사진 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<UpdateMemberImageResult> updateMyImage(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @RequestPart(value="profileImage", required = false) MultipartFile profileImage //TODO DTO로 받으면 415 에러가 뜨는데 이유를 아직 잘 모르겠음 -> 추후 수정하겠음
            ) {
        UpdateMemberImageResult resultDTO = memberCommandService.addMemberProfileImage(auth.id(), profileImage);
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

    @GetMapping("/api/members/part-post")
    @Operation(
            summary = "사용자의 공동구매 참여 목록 조회",
            description = "사용자의 공동구매 참여 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetMyPostList> getPostsByParticipating(
            @AuthenticationPrincipal AuthInfoDTO auth,

            @Parameter(description = "모집 마감 여부(FULL, NOT_FULL)", example = "FULL")
            @RequestParam(name = "status", defaultValue = "NOT_FULL") CapacityStatus status // 첫 화면이 모집 중이므로 defaultValue = "NOT_FULL"로 지정
    ) {
        GetMyPostList resultDTO = memberQueryService.getMyParticipatedPostResult(auth.id(), status);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/members/author-post")
    @Operation(
            summary = "사용자가 모집한 공동구매 목록 조회",
            description = "사용자가 모집한 공동구매 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetMyPostList> getPostsByRecruit(
            @AuthenticationPrincipal AuthInfoDTO auth,

            @Parameter(description = "모집 마감 여부(FULL, NOT_FULL)", example = "FULL")
            @RequestParam(name = "status", defaultValue = "NOT_FULL") CapacityStatus status
    ) {
        GetMyPostList resultDTO = memberQueryService.getMyRecruitingPostResult(auth.id(), status);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

}

package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.service.component.command.member.MemberCommandService;
import site.moamoa.backend.service.component.query.member.MemberQueryService;
import site.moamoa.backend.service.component.query.member_post.MemberPostQueryService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.MemberRequestDTO.UpdateMemberAddress;
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
            @RequestPart(value="profileImage", required = false) MultipartFile profileImage
            ) {
        UpdateMemberImageResult resultDTO = memberCommandService.addMemberProfileImage(auth.id(), profileImage);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PatchMapping("/api/members/location")
    @Operation(
            summary = "사용자 동네 수정",
            description = "사용자의 동네 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<UpdateMemberAddressResult> updateMyLocation(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @RequestBody UpdateMemberAddress request
    ) {
        UpdateMemberAddressResult resultDTO = memberCommandService.updateMemberAddress(auth.id(), request);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/members/posts")
    @Operation(
            summary = "사용자의 공동구매 참여 목록 조회",
            description = "사용자의 공동구매 참여 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetMyPostList> getPostsByParticipating(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @Parameter(description = "모집 중인지 참여 중인지 여부(AUTHOR, PARTICIPATOR)", example = "AUTHOR")
            @RequestParam(name = "isAuthorStatus") final IsAuthorStatus isAuthorStatus,
            @Parameter(description = "모집 마감 여부(FULL, NOT_FULL)", example = "FULL")
            @RequestParam(name = "capacityStatus", defaultValue = "NOT_FULL") final CapacityStatus capacityStatus // 첫 화면이 모집 중이므로 defaultValue = "NOT_FULL"로 지정
    ) {
        GetMyPostList resultDTO = memberQueryService.getMyPostResult(auth.id(), isAuthorStatus, capacityStatus);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PostMapping("/api/members/deactivate")
    @Operation(
            summary = "사용자 회원 탈퇴 (개발중)",
            description = "사용자가 회원 탈퇴 시에 deletion_status 필드가 DELETE로 처리됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<DeleteMemberResult> deActiveMember(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        //todo 탈퇴된 회원은 조회가 안되게 하도록 구현이 필요해보임.
        DeleteMemberResult resultDTO = memberCommandService.deActiveMemberResult(auth.id());
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/members/other/{memberId}")
    @Operation(
            summary = "타 사용자 정보 및 모집 중인 공동구매 조회",
            description = "타 사용자 정보와 모집 중인 공동구매를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetOtherMemberInfo> getOtherMemberInfo(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable(name = "memberId")
            @Positive(message = "멤버ID는 양수입니다.")
            @Schema(description = "멤버ID", example = "1")
            @Parameter(name = "memberId", description = "타 사용자 memberId", example = "1", required = true)
            final Long memberId,
            @RequestParam(name = "status", defaultValue = "NOT_FULL") final CapacityStatus status
    ) {
        GetOtherMemberInfo resultDTO = memberQueryService.getOtherMemberInfo(memberId, status);;
        return ApiResponseDTO.onSuccess(resultDTO);
    }

}

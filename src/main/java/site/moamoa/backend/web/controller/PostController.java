package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.request.PostRequestDTO.UpdatePostInfo;
import site.moamoa.backend.web.dto.response.PostResponseDTO.*;

@Tag(name = "공동구매 게시글 API", description = "공동구매 페이지 관련 API")
@RequiredArgsConstructor
@RestController
public class PostController {

    @GetMapping("/api/posts/ranking")
    @Operation(
            summary = "우리 동네 인기 공동구매 조회 (개발중)",
            description = "조회수를 기반으로 우리 동네 인기 공동구매 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPosts> getPostsByRanking(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetPosts resultDTO = null;  //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts/latest")
    @Operation(
            summary = "최근 모집 시작한 공동구매 조회 (개발중)",
            description = "최근 모집을 시작한 공동구매 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPosts> getPostsByLatest(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetPosts resultDTO = null;  //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts/near")
    @Operation(
            summary = "우리 동네 공동구매 조회 (개발중)",
            description = "우리 동네 공동구매 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPosts> getPostsByNear(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetPosts resultDTO = null;  //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts/recent-keyword")
    @Operation(
            summary = "최근 검색한 키워드로 공동구매 조회 (개발중)",
            description = "최근 검색한 공동구매 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPosts> getPostsByRecentKeyword(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetPosts resultDTO = null;  //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PostMapping("/api/posts")
    @Operation(
            summary = "공동구매 등록 (개발중)",
            description = "공동구매 정보를 받아 새로운 공동구매 게시글을 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<AddPostResult> registerPost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @RequestBody AddPost request
            ) {
        AddPostResult resultDTO = null; //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PatchMapping("/api/posts/{postId}")
    @Operation(
            summary = "공동구매 상세정보 수정 (개발중)",
            description = "공동구매 정보를 받아 기존의 공동구매 게시글을 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<UpdatePostInfoResult> updatePost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @RequestBody UpdatePostInfo request,
            @PathVariable
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        UpdatePostInfoResult resultDTO = null;  //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PatchMapping("/api/posts/{postId}/status")
    @Operation(
            summary = "공동구매 상태 변경 (개발중)",
            description = "기존의 공동구매 상태를 변경합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<UpdatePostStatusResult> updatePostStatus(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        UpdatePostStatusResult resultDTO = null;  //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts/{postId}")
    @Operation(
            summary = "공동구매 상세 조회 (개발중)",
            description = "공동구매 게시글의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPost> getPost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        GetPost resultDTO = null;   //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PostMapping("/api/posts/{postId}/join")
    @Operation(
            summary = "공동구매 참여 (개발중)",
            description = "기존의 공동구매에 참여합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<AddMemberPostResult> joinPost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        AddMemberPostResult resultDTO = null;   //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts")
    @Operation(
            summary = "특정 키워드를 포함하는 공동구매 조회 (개발중)",
            description = "특정 키워드가 포함된 공동구매 게시글 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPostsByKeyword> searchPostsByKeyword(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @Parameter(description = "검색어", example = "사과")
            @RequestParam final String keyword,
            @Parameter(description = "카테고리", example = "식품")
            @RequestParam final String category,
            @Parameter(description = "모집까지 남은 일수", example = "4")
            @RequestParam final Integer dDay,
            @Parameter(description = "전체 모집 인원", example = "5")
            @RequestParam final Integer total,
            @Parameter(description = "최소 금액", example = "3000")
            @RequestParam final Integer minPrice,
            @Parameter(description = "최대 금액", example = "5000")
            @RequestParam final Integer maxPrice
    ) {
        GetPostsByKeyword resultDTO = null; //TODO: 서비스 로직 추가 필요
        return ApiResponseDTO.onSuccess(resultDTO);
    }
}

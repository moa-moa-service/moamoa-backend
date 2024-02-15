package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.service.component.command.keyword.KeywordCommandServiceImpl;
import site.moamoa.backend.service.component.command.post.PostCommandService;
import site.moamoa.backend.service.component.query.post.PostQueryService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.request.PostRequestDTO.UpdatePostInfo;
import site.moamoa.backend.web.dto.response.PostResponseDTO.*;

import java.util.List;


@Tag(name = "공동구매 게시글 API", description = "공동구매 페이지 관련 API")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostQueryService postQueryService;
    private final PostCommandService postCommandService;
    private final KeywordCommandServiceImpl keywordCommandService;

    @GetMapping("/api/posts/ranking")
    @Operation(
            summary = "우리 동네 인기 공동구매 조회",
            description = "조회수를 기반으로 우리 동네 인기 공동구매 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
            @ApiResponse(responseCode = "MEMBER404", description = "해당 사용자를 찾을 수 없습니다.", content = @Content)
    })
    public ApiResponseDTO<GetPosts> getPostsByRanking(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetPosts resultDTO = postQueryService.findPostsByRanking(auth.id());
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts/latest")
    @Operation(
            summary = "최근 모집 시작한 공동구매 조회",
            description = "최근 모집을 시작한 공동구매 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPosts> getPostsByLatest(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetPosts resultDTO = postQueryService.findPostsByLatest();
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts/near")
    @Operation(
            summary = "우리 동네 공동구매 조회 (수정중)",
            description = "우리 동네 공동구매 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
            @ApiResponse(responseCode = "MEMBER404", description = "해당 사용자를 찾을 수 없습니다.", content = @Content)
    })
    public ApiResponseDTO<GetPostsWithAddress> getPostsByNear(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {

        GetPostsWithAddress resultDTO = postQueryService.findPostsByNear(auth.id());
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts/recent-keyword")
    @Operation(
            summary = "최근 검색한 키워드로 공동구매 조회",
            description = "최근 검색한 공동구매 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPosts> getPostsByRecentKeyword(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetPosts resultDTO = postQueryService.findPostsByRecentKeyword(auth.id());
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PostMapping(value = "/api/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "공동구매 등록",
            description = "공동구매 정보를 받아 새로운 공동구매 게시글을 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<AddPostResult> registerPost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @RequestPart(value = "request") AddPost request,
            @RequestPart(value = "files") List<MultipartFile> images
    ) {
        AddPostResult resultDTO = postCommandService.registerPost(auth.id(), request, images);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PatchMapping(value = "/api/posts/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "공동구매 상세정보 수정",
            description = "공동구매 정보를 받아 기존의 공동구매 게시글을 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<UpdatePostInfoResult> updatePost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @RequestPart(value = "request") UpdatePostInfo request,
            @RequestPart(value = "files") List<MultipartFile> images,
            @PathVariable(name = "postId")
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        UpdatePostInfoResult resultDTO = postCommandService.updatePostInfo(auth.id(), request, images, postId);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PatchMapping("/api/posts/{postId}/status")
    @Operation(
            summary = "공동구매 상태 변경",
            description = "기존의 공동구매 상태를 변경합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<UpdatePostStatusResult> updatePostStatus(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable(name = "postId")
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        UpdatePostStatusResult resultDTO = postCommandService.updatePostStatus(auth.id(), postId);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PostMapping("/api/posts/{postId}")
    @Operation(
            summary = "공동구매 상세 조회",
            description = "공동구매 게시글의 상세 정보를 조회하고 조회수를 증가시킵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
            @ApiResponse(responseCode = "POST404", description = "해당 게시물을 찾을 수 없습니다.", content = @Content)
    })
    public ApiResponseDTO<GetPost> updateViewCountAndGetPost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable(name = "postId")
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        postCommandService.updatePostViewCount(auth.id(), postId);
        GetPost resultDTO = postQueryService.fetchDetailedPostByPostId(auth.id(), postId);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @PostMapping("/api/posts/{postId}/join")
    @Operation(
            summary = "공동구매 참여 (수정중)",
            description = "기존의 공동구매에 참여합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<AddMemberPostResult> joinPost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable(name = "postId")
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        AddMemberPostResult resultDTO = postCommandService.joinPost(auth.id(), postId);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @DeleteMapping(value = "/api/posts/{postId}/cancel")
    @Operation(
            summary = "공동구매 참여 취소",
            description = "참여한 공동구매에서 참여를 취소합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<DeleteMemberPostResult> cancelPost(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable(name = "postId")
            @Positive(message = "게시글 ID는 양수입니다.")
            @Schema(description = "게시글 ID", example = "1")
            Long postId
    ) {
        DeleteMemberPostResult resultDTO = postCommandService.cancelPost(auth.id(), postId);
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/posts")
    @Operation(
            summary = "특정 키워드를 포함하는 공동구매 조회",
            description = "특정 키워드가 포함된 공동구매 게시글 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetPosts> searchPostsByKeyword(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @Parameter(description = "검색어", example = "사과")
            @RequestParam(value = "keyword", required = false) final String keyword,
            @Parameter(description = "카테고리ID", example = "1")
            @RequestParam(value = "categoryId", required = false) final Long categoryId,
            @Parameter(description = "모집까지 남은 일수", example = "4")
            @RequestParam(value = "dDay", required = false) final Integer dDay,
            @Parameter(description = "전체 모집 인원", example = "5")
            @RequestParam(value = "total", required = false) final Integer total,
            @Parameter(description = "최소 금액", example = "3000")
            @RequestParam(value = "minPrice", required = false) final Integer minPrice,
            @Parameter(description = "최대 금액", example = "5000")
            @RequestParam(value = "maxPrice", required = false) final Integer maxPrice
    ) {
        if (keyword != null) {
            keywordCommandService.addMemberKeyword(auth.id(), keyword);
            keywordCommandService.updateTownKeywordCount(auth.id(), keyword);
        }
        GetPosts resultDTO = postQueryService.findPostsByConditions(keyword, categoryId, dDay, total, minPrice, maxPrice);
        return ApiResponseDTO.onSuccess(resultDTO);
    }
}
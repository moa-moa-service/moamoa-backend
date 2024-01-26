package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import site.moamoa.backend.api_payload.ApiResponseDTO;
import site.moamoa.backend.service.keyword.query.KeywordQueryService;
import site.moamoa.backend.service.member.query.MemberQueryService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.response.KeywordResponseDTO;

import static site.moamoa.backend.web.dto.response.KeywordResponseDTO.GetKeywords;
import static site.moamoa.backend.web.dto.response.KeywordResponseDTO.DeleteKeywordResult;

@Tag(name = "검색어 API", description = "검색어 관련 API")
@RequiredArgsConstructor
@RestController
public class KeywordController {

    private final KeywordQueryService keywordQueryService;
    private final MemberQueryService memberQueryService;

    @GetMapping("/api/keywords/ranking")
    @Operation(
            summary = "우리 동네 인기 검색어 조회",
            description = "조회된 검색량을 기반으로 우리 동네 인기 검색어 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetKeywords> getKeywordsByRanking(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetKeywords resultDTO = keywordQueryService.popularSearchRankList(memberQueryService.findMemberById(auth.id()).getTown());
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/keywords/recent")
    @Operation(
            summary = "사용자의 최근 검색어 조회",
            description = "사용자가 최근에 검색한 검색어 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<GetKeywords> getKeywordsByRecent(
            @AuthenticationPrincipal AuthInfoDTO auth
    ) {
        GetKeywords resultDTO = keywordQueryService.recentSearchRankList(auth.id());
        return ApiResponseDTO.onSuccess(resultDTO);
    }

    @GetMapping("/api/keywords/{keyword}")
    @Operation(
            summary = "사용자의 최근 검색어 삭제 (개발중)",
            description = "사용자가 본인의 최근 검색어를 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<DeleteKeywordResult> deleteMemberKeyword(
            @AuthenticationPrincipal AuthInfoDTO auth,
            @PathVariable("keyword") String keyword
    ) {
        DeleteKeywordResult deleteKeywordResult = keywordQueryService.deleteRecentKeyword(auth.id(), keyword);
        return ApiResponseDTO.onSuccess(deleteKeywordResult);
    }

}

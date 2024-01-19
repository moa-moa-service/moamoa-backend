package site.moamoa.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.moamoa.backend.api_payload.ApiResponseDTO;

@Tag(name = "검색어 API", description = "검색어 관련 API")
@RequiredArgsConstructor
@RestController
public class KeywordController {

    @GetMapping("/api/keywords/ranking")
    @Operation(
            summary = "우리 동네 인기 검색어 조회 (개발중)",
            description = "조회된 검색량을 기반으로 우리 동네 인기 검색어 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<?> getKeywordsByRanking(
            //TODO: Security 추가시 인증부 구현 필요
    ) {
        //TODO: 서비스 로직 추가 필요
        return null;    //TODO: Result DTO 반환 필요
    }

    @GetMapping("/api/keywords/recent")
    @Operation(
            summary = "사용자의 최근 검색어 조회 (개발중)",
            description = "사용자가 최근에 검색한 검색어 리스트를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public ApiResponseDTO<?> getKeywordsByRecent(
            //TODO: Security 추가시 인증부 구현 필요
    ) {
        //TODO: 서비스 로직 추가 필요
        return null;    //TODO: Result DTO 반환 필요
    }

}

package site.moamoa.backend.web.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.moamoa.backend.web.dto.base.MemberDTO;

public class MemberResponseDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class AddMemberInfoResult {
        Long memberId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetMyInfoResult {
        MemberDTO memberDTO;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class UpdatedMemberImageResult {
        Long userId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class UpdatedMemberAddressResult {
        Long userId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetMemberInfo {
        MemberDTO memberDto;
    }

}

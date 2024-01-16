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
        Long member_id;
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
        Long user_id;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class UpdatedMemberAddressResult {
        Long user_id;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetMemberInfo {
        MemberDTO memberDto;
    }

}

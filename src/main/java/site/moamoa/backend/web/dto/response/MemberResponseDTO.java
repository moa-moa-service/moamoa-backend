package site.moamoa.backend.web.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import site.moamoa.backend.web.dto.base.MemberDTO;

public class MemberResponseDTO {

    @Builder
    public record AddMemberInfoResult(
            Long memberId,
            LocalDateTime createdAt
    ) {

    }

    @Builder
    public record GetMyInfoResult(
            MemberDTO memberDTO
    ) {
    }

    @Builder
    public record UpdateMemberImageResult(
            Long userId,
            LocalDateTime updatedAt
    ) {
    }

    @Builder
    public record UpdateMemberAddressResult(
            Long userId,
            LocalDateTime updatedAt
    ) {

    }

    @Builder
    public record GetMemberInfo(
            MemberDTO memberDto
    ) {
    }

}

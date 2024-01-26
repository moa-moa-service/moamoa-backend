package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.web.dto.base.MemberDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

public class MemberConverter {
    public static MemberResponseDTO.UpdateMemberImageResult toUpdateMemberImageDTO(Member member) {
        return MemberResponseDTO.UpdateMemberImageResult.builder()
                .userId(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static MemberResponseDTO.GetMyInfoResult toMemberDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .townName(member.getTown())
                .build();

        return MemberResponseDTO.GetMyInfoResult.builder()
                .memberDTO(memberDTO)
                .build();
    }

    public static MemberResponseDTO.UpdateMemberAddressResult updateMemberAddressResult(Member member) {
        return MemberResponseDTO.UpdateMemberAddressResult.builder()
                .userId(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static MemberResponseDTO.DeleteMemberResult deActiveMemberResult(Member member) {
        return MemberResponseDTO.DeleteMemberResult.builder()
                .userId(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}

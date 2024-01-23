package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

public class MemberConverter {
    public static MemberResponseDTO.UpdateMemberImageResult toUpdateMemberImageDTO(Member member) {
        return MemberResponseDTO.UpdateMemberImageResult.builder()
                .userId(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}

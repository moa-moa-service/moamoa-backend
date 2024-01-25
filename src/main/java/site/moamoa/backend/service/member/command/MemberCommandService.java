package site.moamoa.backend.service.member.command;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

public interface MemberCommandService {
    MemberResponseDTO.UpdateMemberImageResult addMemberProfileImage(Long memberId, MultipartFile profileImage);

    MemberResponseDTO.UpdateMemberAddressResult updateMemberAddress(Long memberId, MemberRequestDTO.UpdateMemberAddress request);

    MemberResponseDTO.DeleteMemberResult deActiveMemberResult(Long memberId);
}
package site.moamoa.backend.service.component.command.member;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.global.oauth2.CustomOAuth2User;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

public interface MemberCommandService {
    MemberResponseDTO.UpdateMemberImageResult addMemberProfileImage(Long memberId, MultipartFile profileImage);

    MemberResponseDTO.UpdateMemberAddressResult updateMemberAddress(Long memberId, MemberRequestDTO.UpdateMemberAddress request);

    MemberResponseDTO.DeleteMemberResult deActiveMemberResult(Long memberId);

    void memberSetRefreshToken(CustomOAuth2User oAuth2User, String refreshToken);

    MemberResponseDTO.LogoutInfo memberDeleteRefreshToken(Long memberId);

    MemberResponseDTO.AddMemberInfoResult addMemberInfo(Long memberId, MemberRequestDTO.AddMemberInfo memberInfo);
}

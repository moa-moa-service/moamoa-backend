package site.moamoa.backend.service;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

public interface MemberCommandService {
    MemberResponseDTO.UpdateMemberImageResult addMemberProfileImage(Long memberId, MultipartFile profileImage);
}

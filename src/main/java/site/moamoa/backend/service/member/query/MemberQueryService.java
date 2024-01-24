package site.moamoa.backend.service.member.query;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

public interface MemberQueryService {
    Member findMemberById(Long id);

    PostResponseDTO.GetMyPostList getMyParticipatedPostResult(Long memberId, CapacityStatus status);

    PostResponseDTO.GetMyPostList getMyRecruitingPostResult(Long memberId, CapacityStatus status);

    MemberResponseDTO.GetMyInfoResult getMyInfo(Long memberId);
}

package site.moamoa.backend.service;

import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

public interface MemberQueryService {
    PostResponseDTO.GetMyPostList getMyParticipatedPostResult(Long memberId, CapacityStatus status);
    PostResponseDTO.GetMyPostList getMyRecruitingPostResult(Long memberId, CapacityStatus status);
}

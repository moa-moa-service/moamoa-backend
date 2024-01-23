package site.moamoa.backend.service;

import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

public interface MemberQueryService {
    PostResponseDTO.GetMyPostList GetMyParticipatedPostResult(Long memberId, CapacityStatus status);
}

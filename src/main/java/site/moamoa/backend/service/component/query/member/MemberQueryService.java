package site.moamoa.backend.service.component.query.member;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

public interface MemberQueryService {

    PostResponseDTO.GetMyPostList getMyPostResult(Long memberId, IsAuthorStatus isAuthorStatus, CapacityStatus capacityStatus);


    MemberResponseDTO.GetMyInfoResult getMyInfo(Long memberId);

    Member findMemberById(Long memberId);

    MemberResponseDTO.GetOtherMemberInfo getOtherMemberInfo(Long memberId, CapacityStatus status);
}

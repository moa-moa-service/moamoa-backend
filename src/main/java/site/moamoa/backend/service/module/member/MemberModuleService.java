package site.moamoa.backend.service.module.member;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;

import java.util.List;

public interface MemberModuleService {
    List<Post> findRecruitingMember(Long memberId, CapacityStatus status);
    List<Post> findParticipatedMember(Long memberId, CapacityStatus status);

    Member findMemberById(Long memberId);
}

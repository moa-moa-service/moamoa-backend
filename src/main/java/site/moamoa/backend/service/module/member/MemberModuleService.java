package site.moamoa.backend.service.module.member;

import site.moamoa.backend.domain.Member;

public interface MemberModuleService {
    Member findMemberById(Long memberId);
}

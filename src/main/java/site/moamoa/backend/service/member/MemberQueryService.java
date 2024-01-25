package site.moamoa.backend.service.member;

import site.moamoa.backend.domain.Member;

public interface MemberQueryService {
    Member findMemberById(Long id);
}

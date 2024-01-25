package site.moamoa.backend.service.member.query;

import site.moamoa.backend.domain.Member;

public interface MemberQueryService {
    Member findMemberById(Long id);
}

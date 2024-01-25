package site.moamoa.backend.service;

import site.moamoa.backend.domain.Member;

public interface MemberQueryService {
    Member findMemberById(Long id);
}

package site.moamoa.backend.web.service;

import site.moamoa.backend.domain.Member;

public interface MemberQueryService {
    Member findMemberById(Long id);
}

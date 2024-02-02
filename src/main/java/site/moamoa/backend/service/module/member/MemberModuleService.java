package site.moamoa.backend.service.module.member;

import site.moamoa.backend.domain.Member;

import java.util.Optional;

public interface MemberModuleService {
    Member findMemberById(Long memberId);

    Optional<Member> findMemberBySocialId(String socialId);

    Member save(Member member);
}

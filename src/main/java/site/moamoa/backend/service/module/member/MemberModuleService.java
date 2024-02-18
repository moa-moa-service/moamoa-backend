package site.moamoa.backend.service.module.member;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.enums.DeletionStatus;

import java.util.List;
import java.util.Optional;

public interface MemberModuleService {
    Member findMemberById(Long memberId);

    Optional<Member> findMemberBySocialId(String socialId);

    Member save(Member member);

    List<Member> findMembersToSoftDelete(DeletionStatus deletionStatus);

    void deleteAllByIdInBatch(List<Long> memberIds);
}

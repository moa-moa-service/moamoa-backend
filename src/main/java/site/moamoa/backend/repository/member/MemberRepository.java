package site.moamoa.backend.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.enums.DeletionStatus;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findBySocialId(String socialId);

    @Query("SELECT m FROM Member m WHERE m.deletionStatus = :deletionStatus")
    List<Member> findMembersToSoftDelete(@Param("deletionStatus") DeletionStatus deletionStatus);

    @Modifying
    @Query("DELETE FROM Member m WHERE m.id IN :memberIds")
    void deleteMembersByIds(@Param("memberIds") List<Long> memberIds);
}

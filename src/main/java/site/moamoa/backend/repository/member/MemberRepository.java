package site.moamoa.backend.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moamoa.backend.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findBySocialId(String socialId);
}

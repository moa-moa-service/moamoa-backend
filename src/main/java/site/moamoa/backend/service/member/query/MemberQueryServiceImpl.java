package site.moamoa.backend.service.member.query;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.repository.member.MemberRepository;
import site.moamoa.backend.exception.member.MemberNotFoundException;

@AllArgsConstructor
@Service
public class MemberQueryServiceImpl implements MemberQueryService {

  private final MemberRepository memberRepository;

  @Override
  public Member findMemberById(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(
            () -> new MemberNotFoundException()
        );
  }
}

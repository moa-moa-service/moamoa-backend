package site.moamoa.backend.web.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.repository.MemberRepository;
import site.moamoa.backend.web.exception.MemberNotFoundException;

@AllArgsConstructor
@Service
public class MemberQueryServiceImpl implements MemberQueryService{

  private final MemberRepository memberRepository;

  @Override
  public Member findMemberById(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(
            () -> new MemberNotFoundException()
        );
  }
}

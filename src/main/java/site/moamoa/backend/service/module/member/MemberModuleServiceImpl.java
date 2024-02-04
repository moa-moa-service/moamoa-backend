package site.moamoa.backend.service.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.repository.member.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberModuleServiceImpl implements MemberModuleService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public Optional<Member> findMemberBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
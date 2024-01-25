package site.moamoa.backend.service.member.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.repository.mapping.MemberPostRepository;
import site.moamoa.backend.repository.member.MemberRepository;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;
    private final MemberPostRepository memberPostRepository;

    @Override
    public PostResponseDTO.GetMyPostList getMyParticipatedPostResult(Long memberId, CapacityStatus status) {
        List<Post> participatedMember = memberPostRepository.findParticipatedMember(memberId, status);
        return PostConverter.toMyParticipatedOrRecruitingPostResult(memberId, participatedMember);
    }

    @Override
    public PostResponseDTO.GetMyPostList getMyRecruitingPostResult(Long memberId, CapacityStatus status) {
        List<Post> participatedMember = memberPostRepository.findRecruitingMember(memberId, status);
        return PostConverter.toMyParticipatedOrRecruitingPostResult(memberId, participatedMember);
    }

    @Override
    public MemberResponseDTO.GetMyInfoResult getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        return MemberConverter.toMemberDTO(member);
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }
}

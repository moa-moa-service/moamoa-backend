package site.moamoa.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.repository.MemberPostRepository;
import site.moamoa.backend.repository.MemberRepository;
import site.moamoa.backend.web.dto.base.MemberDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberPostRepository memberPostRepository;
    private final MemberRepository memberRepository;

    @Override
    public PostResponseDTO.GetMyPostList getMyParticipatedPostResult(Long memberId, CapacityStatus status) {
        List<Post> participatedMember = memberPostRepository.findParticipatedMember(memberId, status);
        return MemberConverter.toMyParticipatedOrRecruitingPostResult(memberId, participatedMember);
    }

    @Override
    public PostResponseDTO.GetMyPostList getMyRecruitingPostResult(Long memberId, CapacityStatus status) {
        List<Post> participatedMember = memberPostRepository.findRecruitingMember(memberId, status);
        return MemberConverter.toMyParticipatedOrRecruitingPostResult(memberId, participatedMember);
    }

    @Override
    public MemberResponseDTO.GetMyInfoResult getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        return MemberConverter.toMemberDTO(member);
    }
}

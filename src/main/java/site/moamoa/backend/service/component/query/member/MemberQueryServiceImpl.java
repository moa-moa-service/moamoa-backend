package site.moamoa.backend.service.component.query.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.service.module.member_post.MemberPostModuleService;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberModuleService memberModuleService;
    private final MemberPostModuleService memberPostModuleService;

    @Override
    public PostResponseDTO.GetMyPostList getMyPostResult(Long memberId, IsAuthorStatus isAuthorStatus, CapacityStatus capacityStatus) {
        List<Post> participatedMember = memberPostModuleService.findPostsByRecruitingAndParticipating(memberId, isAuthorStatus, capacityStatus);
        return PostConverter.toMyParticipatedOrRecruitingPostResult(memberId, participatedMember);
    }

    @Override
    public MemberResponseDTO.GetMyInfoResult getMyInfo(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberConverter.toGetMyInfoResult(member);
    }

    @Override
    public MemberResponseDTO.GetOtherMemberInfo getOtherMemberInfo(Long memberId, CapacityStatus status) {
        Member member = memberModuleService.findMemberById(memberId);
        List<Post> posts = memberPostModuleService.findPostsByRecruitingAndParticipating(memberId, IsAuthorStatus.AUTHOR, status);
        return PostConverter.toOtherRecruitingPostResult(member, posts);
    }

    @Override
    public Member findMemberById(Long memberId) {
        return memberModuleService.findMemberById(memberId);
    }
}
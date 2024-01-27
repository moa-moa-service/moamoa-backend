package site.moamoa.backend.service.component.command.member;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.repository.member.MemberRepository;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public MemberResponseDTO.UpdateMemberImageResult addMemberProfileImage(Long memberId, MultipartFile updateMemberImageDto) {
        Member member = findMemberById(memberId);
        String memberProfileUrl = null;
        if (updateMemberImageDto != null && !updateMemberImageDto.isEmpty()) {
            memberProfileUrl = amazonS3Manager.uploadImage(amazonS3Manager.generateMemberProfileKeyName(), updateMemberImageDto);
        }

        member.addProfileImage(memberProfileUrl);
        return MemberConverter.toUpdateMemberImageDTO(member);
    }

    @Override
    public MemberResponseDTO.UpdateMemberAddressResult updateMemberAddress(Long memberId, MemberRequestDTO.UpdateMemberAddress request) {
        Member member = findMemberById(memberId);
        member.addInfo(member.getNickname(), request.address());
        return MemberConverter.updateMemberAddressResult(member);
    }

    @Override
    public MemberResponseDTO.DeleteMemberResult deActiveMemberResult(Long memberId) {
        Member member = findMemberById(memberId);
        member.deactivate();
        return MemberConverter.deActiveMemberResult(member);
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

}

package site.moamoa.backend.service.component.command.member;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.global.oauth2.CustomOAuth2User;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberModuleService memberModuleService;
    private final AmazonS3Manager amazonS3Manager;
    @Value("${default.profileImage.url}")
    private String defaultImageUrl;

    @Override
    public MemberResponseDTO.UpdateMemberImageResult addMemberProfileImage(Long memberId, MultipartFile updateMemberImageDto) {
        Member member = memberModuleService.findMemberById(memberId);
        String memberProfileUrl = defaultImageUrl;

        if (updateMemberImageDto != null) {
            memberProfileUrl = amazonS3Manager.uploadImage(amazonS3Manager.generateMemberProfileKeyName(), updateMemberImageDto);
        }

        String profileImage = member.getProfileImage();
        if (!profileImage.equals(defaultImageUrl)) {
            String key = amazonS3Manager.extractImageNameFromUrl(profileImage);
            amazonS3Manager.deleteImage(key);
        }

        member.addProfileImage(memberProfileUrl);
        return MemberConverter.toUpdateMemberImageDTO(member);
    }

    @Override
    public MemberResponseDTO.UpdateMemberAddressResult updateMemberAddress(Long memberId, MemberRequestDTO.UpdateMemberAddress request) {
        Member member = memberModuleService.findMemberById(memberId);
        member.addInfo(member.getNickname(), request.town(), request.address());
        return MemberConverter.updateMemberAddressResult(member);
    }

    @Override
    public MemberResponseDTO.DeleteMemberResult deActiveMemberResult(Long memberId) {
        Member member = memberModuleService.findMemberById(memberId);
        member.deactivate();
        return MemberConverter.deActiveMemberResult(member);
    }

    @Override
    public void memberSetRefreshToken(CustomOAuth2User oAuth2User, String refreshToken) {
        Member member = memberModuleService.findMemberById(oAuth2User.getId());
        member.addRefreshToken(refreshToken);
    }

    @Override
    public MemberResponseDTO.LogoutInfo memberDeleteRefreshToken(Long memberId) {
        Member member = memberModuleService.findMemberById(memberId);
        member.addRefreshToken(null);
        return MemberConverter.logoutMemberInfoResult(member);
    }

    @Override
    public MemberResponseDTO.AddMemberInfoResult addMemberInfo(Long memberId, MemberRequestDTO.AddMemberInfo memberInfo) {
        Member member = memberModuleService.findMemberById(memberId);
        if (member.getNickname() != null) {
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXISTS);
        }
        member.addInfo(memberInfo.nickname(), memberInfo.town(), memberInfo.address());
        return MemberConverter.addMemberInfoResult(member);
    }
}

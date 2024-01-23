package site.moamoa.backend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.aws.s3.AmazonS3Manager;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.repository.MemberRepository;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public MemberResponseDTO.UpdateMemberImageResult addMemberProfileImage(Long memberId, MultipartFile updateMemberImageDto) {
        String memberProfileUrl = amazonS3Manager.uploadImage(amazonS3Manager.generateMemberProfileKeyName(), updateMemberImageDto);
        Member member = memberRepository.findById(memberId).orElseThrow((RuntimeException::new));
        member.addProfileImage(memberProfileUrl);
        return MemberConverter.toUpdateMemberImageDTO(member);
    }
}

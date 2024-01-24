package site.moamoa.backend.converter;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.web.dto.base.MemberDTO;
import site.moamoa.backend.web.dto.base.SimplePostDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {
    public static MemberResponseDTO.UpdateMemberImageResult toUpdateMemberImageDTO(Member member) {
        return MemberResponseDTO.UpdateMemberImageResult.builder()
                .userId(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static PostResponseDTO.GetMyPostList toMyParticipatedOrRecruitingPostResult(Long memberId, List<Post> postList) {
        List<SimplePostDTO> simplePostDTOS = postList.stream()
                .map(MemberConverter::toSimplePostDTO)
                .toList();

        return PostResponseDTO.GetMyPostList.builder()
                .userId(memberId)
                .simplePostDtoList(simplePostDTOS)
                .build();
    }
    public static SimplePostDTO toSimplePostDTO(Post post) {
        getDday(post.getDeadline());

        return SimplePostDTO.builder()
                .postId(post.getId())
                .image(null) //todo 맨 앞 이미지 가져오게 변경
                .productName(post.getProductName())
                .personnel(post.getPersonnel())
                .dDay(getDday(post.getDeadline()))
                .price(post.getTotalPrice())
                .status(post.getCapacityStatus())
                .build();
    }

    public static MemberResponseDTO.GetMyInfoResult toMemberDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .townName(member.getTown())
                .build();

        return MemberResponseDTO.GetMyInfoResult.builder()
                .memberDTO(memberDTO)
                .build();
    }

    public static MemberResponseDTO.UpdateMemberAddressResult updateMemberAddressResult(Member member) {
        return MemberResponseDTO.UpdateMemberAddressResult.builder()
                .userId(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static MemberResponseDTO.DeleteMemberResult deActiveMemberResult(Member member) {
        return MemberResponseDTO.DeleteMemberResult.builder()
                .userId(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    //dday 구하기
    private static int getDday(LocalDateTime deadLine) {
        LocalDateTime now = LocalDateTime.now();
        return (int) now.until(deadLine, ChronoUnit.DAYS);
    }
}

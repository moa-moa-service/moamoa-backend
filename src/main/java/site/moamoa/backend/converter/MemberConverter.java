package site.moamoa.backend.converter;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.web.dto.base.SimplePostDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {
    public static MemberResponseDTO.UpdateMemberImageResult toUpdateMemberImageDTO(Member member) {
        return MemberResponseDTO.UpdateMemberImageResult.builder()
                .userId(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static PostResponseDTO.GetMyPostList toMyParticipatedPostResult(Long memberId, List<Post> postList) {
        List<SimplePostDTO> simplePostDTOS = postList.stream()
                .map(MemberConverter::toSimplePostDTO)
                .toList();

        return PostResponseDTO.GetMyPostList.builder()
                .userId(memberId)
                .simplePostDtoList(simplePostDTOS)
                .build();
    }
    public static SimplePostDTO toSimplePostDTO(Post post) {
        return SimplePostDTO.builder()
                .image(null) //todo 맨 앞 이미지 가져오게 변경
                .productName(post.getProductName())
                .personnel(post.getPersonnel())
                .dDay(Period.between(LocalDate.now(), post.getDeadline().toLocalDate()).getDays())
                .price(post.getTotalPrice())
                .status(post.getCapacityStatus())
                .build();
    }
}

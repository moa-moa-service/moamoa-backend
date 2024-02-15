package site.moamoa.backend.converter;

import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddMemberPostResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.DeleteMemberPostResult;

import java.time.LocalDateTime;

public class MemberPostConverter {

    public static MemberPost toMemberPostAsAuthor() {
        return MemberPost.builder()
                .isAuthorStatus(IsAuthorStatus.AUTHOR)
                .amount(0)
                .build();
    }

    public static MemberPost toMemberPostAsParticipator(Integer amount) {
        return MemberPost.builder()
                .isAuthorStatus(IsAuthorStatus.PARTICIPATOR)
                .amount(amount)
                .build();
    }

    public static AddMemberPostResult toAddMemberPostResult(MemberPost memberPost) {
        return AddMemberPostResult.builder()
                .memberPostId(memberPost.getId())
                .createdAt(memberPost.getCreatedAt())
                .build();
    }

    public static DeleteMemberPostResult toDeleteMemberPostResult(MemberPost memberPost) {
        return DeleteMemberPostResult.builder()
                .memberPostId(memberPost.getId())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
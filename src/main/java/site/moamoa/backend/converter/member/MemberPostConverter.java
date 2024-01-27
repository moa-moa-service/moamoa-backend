package site.moamoa.backend.converter.member;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddMemberPostResult;

public class MemberPostConverter {

  public static MemberPost toMemberPostAsAuthor(Post post, Member member) {
    return MemberPost.builder()
        .post(post)
        .member(member)
        .isAuthorStatus(IsAuthorStatus.AUTHOR)
        .build();
  }

  public static MemberPost toMemberPostAsParticipator(Post post, Member member) {
    return MemberPost.builder()
        .post(post)
        .member(member)
        .isAuthorStatus(IsAuthorStatus.PARTICIPATOR)
        .build();
  }

  public static AddMemberPostResult toAddMemberPostResult(MemberPost memberPost){
    return AddMemberPostResult.builder()
        .memberPostId(memberPost.getId())
        .createdAt(memberPost.getCreatedAt())
        .build();
  }
}

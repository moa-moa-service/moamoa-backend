package site.moamoa.backend.web.converter;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.mapping.MemberPost;

public class MemberPostConverter {
  public static MemberPost toMemberPost(Post post, Member member){
    return MemberPost.builder()
        .post(post)
        .member(member)
        .build();
  }
}

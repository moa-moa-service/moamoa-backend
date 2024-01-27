package site.moamoa.backend.service.module.member_post;

import site.moamoa.backend.domain.mapping.MemberPost;

public interface MemberPostModuleService {
    MemberPost saveMemberPost(MemberPost memberPost);

    void validMemberPostIsAuthor(Long memberId, Long postId);
}

package site.moamoa.backend.service.post.command;

import site.moamoa.backend.web.dto.base.SimplePostDTO;

import java.util.List;

public interface PostCommandService {
    void updatePostViewCount(Long memberId, Long postId);
}

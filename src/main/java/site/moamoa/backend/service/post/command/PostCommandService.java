package site.moamoa.backend.service.post.command;

import site.moamoa.backend.web.dto.base.SimplePostDTO;

import java.util.List;

public interface PostCommandService {
    void updateKeywordCount(Long memberId, String keyword);

    void updatePostViewCount(Long memberId, Long postId);
}

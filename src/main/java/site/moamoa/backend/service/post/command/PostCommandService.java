package site.moamoa.backend.service.post.command;

public interface PostCommandService {
    void updateKeywordCount(Long memberId, String keyword);

    void updatePostViewCount(Long memberId, Long postId);
}

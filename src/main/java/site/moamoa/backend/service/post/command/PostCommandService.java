package site.moamoa.backend.service.post.command;

public interface PostCommandService {
    void updatePostViewCount(Long memberId, Long postId);
}

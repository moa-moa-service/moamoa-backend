package site.moamoa.backend.service.module.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.CommentHandler;
import site.moamoa.backend.domain.Comment;
import site.moamoa.backend.repository.comment.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentModuleServiceImpl implements CommentModuleService {

    private final CommentRepository commentRepository;

    @Override
    public Comment findCommentById(Long parentId) {
        return commentRepository.findById(parentId)
                .orElseThrow(() -> new CommentHandler(ErrorStatus.COMMENT_NOT_FOUND));
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
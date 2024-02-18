package site.moamoa.backend.service.module.comment;

import site.moamoa.backend.domain.Comment;

import java.util.List;

public interface CommentModuleService {
    Comment findCommentById(Long parentId);

    Comment saveComment(Comment comment);

    void deleteCommentsByIdInBatch(List<Long> commentIds);
}
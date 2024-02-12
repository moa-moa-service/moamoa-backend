package site.moamoa.backend.service.module.comment;

import site.moamoa.backend.domain.Comment;

public interface CommentModuleService {
    Comment findCommentById(Long parentId);

    Comment saveComment(Comment comment);
}
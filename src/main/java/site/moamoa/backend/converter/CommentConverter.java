package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Comment;
import site.moamoa.backend.web.dto.base.CommentDTO;
import site.moamoa.backend.web.dto.response.CommentResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

import static site.moamoa.backend.web.dto.request.CommentRequestDTO.AddComment;

public class CommentConverter {

    public static List<CommentDTO> toCommentDTOList(List<Comment> commentList) {
        return commentList.stream()
                .map(CommentConverter::toCommentDTO)
                .collect(Collectors.toList());
    }

    public static CommentDTO toCommentDTO(Comment comment) {
        Comment parentComment = comment.getParent();
        return CommentDTO.builder()
                .commentId(comment.getId())
                .parentCommentId(parentComment != null ? parentComment.getId() : null)
                .nickname(comment.getMember().getNickname())
                .childrenCommentDtoList(toCommentDTOList(comment.getChildren()))
                .content(comment.getContent())
                .profileImage(comment.getMember().getProfileImage())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static Comment toComment(AddComment addComment) {
        return Comment.builder()
                .content(addComment.content())
                .build();
    }

    public static CommentResponseDTO.AddCommentResult toAddCommentResult(Comment comment) {
        return CommentResponseDTO.AddCommentResult.builder()
                .commentId(comment.getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
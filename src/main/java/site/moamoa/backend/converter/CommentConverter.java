package site.moamoa.backend.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import site.moamoa.backend.domain.Comment;
import site.moamoa.backend.web.dto.base.CommentDTO;

public class CommentConverter {

  public static List<CommentDTO> toCommentDTOList(List<Comment> commentList) {
    return commentList.stream()
        .map(comment -> CommentDTO.builder()
            .commentId(comment.getId())
            .memberId(comment.getMember().getId())
            .childrenCommentDtoList(toCommentDTOList(comment.getChildren()))
            .parentCommentDto(
                Optional.ofNullable(comment.getParent()).map(parent -> toCommentDTO(parent)).orElse(null))
            .content(comment.getContent())
            .build())
        .collect(Collectors.toList());
  }

  public static CommentDTO toCommentDTO(Comment comment) {
    return CommentDTO.builder()
        .commentId(comment.getId())
        .memberId(comment.getMember().getId())
        .childrenCommentDtoList(toCommentDTOList(comment.getChildren()))
        .parentCommentDto(toCommentDTO(comment.getParent()))
        .content(comment.getContent())
        .build();
  }
}

package site.moamoa.backend.service.component.command.comment;

import site.moamoa.backend.web.dto.request.CommentRequestDTO;
import site.moamoa.backend.web.dto.response.CommentResponseDTO;

public interface CommentCommandService {
    CommentResponseDTO.AddCommentResult registerComment(Long memberId, Long noticeId, CommentRequestDTO.AddComment request);
}
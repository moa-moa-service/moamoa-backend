package site.moamoa.backend.service.component.command.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.CommentConverter;
import site.moamoa.backend.domain.Comment;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.service.module.comment.CommentModuleService;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.service.module.notice.NoticeModuleService;
import site.moamoa.backend.web.dto.response.CommentResponseDTO;

import static site.moamoa.backend.web.dto.request.CommentRequestDTO.AddComment;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentModuleService commentModuleService;
    private final MemberModuleService memberModuleService;
    private final NoticeModuleService noticeModuleService;

    @Override
    public CommentResponseDTO.AddCommentResult registerComment(Long memberId, Long noticeId, AddComment request) {

        Member member = memberModuleService.findMemberById(memberId);
        Notice notice = noticeModuleService.findNoticeById(noticeId);

        Comment newComment = CommentConverter.toComment(request);

        newComment.setMember(member);
        newComment.setNotice(notice);
        if (request.parentId() != null) {
            newComment.setParent(commentModuleService.findCommentById(request.parentId()));
        }

        commentModuleService.saveComment(newComment);
        return CommentConverter.toAddCommentResult(newComment);
    }
}
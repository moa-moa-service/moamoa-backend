package site.moamoa.backend.service.component.command.comment;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.CommentConverter;
import site.moamoa.backend.domain.Comment;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Notification;
import site.moamoa.backend.domain.enums.NotificationStatus;
import site.moamoa.backend.domain.enums.NotificationType;
import site.moamoa.backend.service.module.comment.CommentModuleService;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.service.module.member_post.MemberPostModuleService;
import site.moamoa.backend.service.module.notice.NoticeModuleService;
import site.moamoa.backend.service.module.notification.NotificationModuleService;
import site.moamoa.backend.web.dto.response.CommentResponseDTO;

import static site.moamoa.backend.web.dto.request.CommentRequestDTO.AddComment;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentModuleService commentModuleService;
    private final MemberModuleService memberModuleService;
    private final NoticeModuleService noticeModuleService;
    private final MemberPostModuleService memberPostModuleService;
    private final NotificationModuleService notificationModuleService;


    @Override
    public CommentResponseDTO.AddCommentResult registerComment(Long memberId, Long noticeId, AddComment request) {

        Member authMember = memberModuleService.findMemberById(memberId);
        Notice notice = noticeModuleService.findNoticeById(noticeId);

        Comment newComment = CommentConverter.toComment(request);

        newComment.setMember(authMember);
        newComment.setNotice(notice);

        if (request.parentId() != null) {
            newComment.setParent(commentModuleService.findCommentById(request.parentId()));
        }

        Long postId = notice.getPost().getId();
        commentModuleService.saveComment(newComment);

        List<Notification> notifications = memberPostModuleService.findParticipatingMembersExcludingMember(postId, memberId)
            .stream().map(member -> Notification.builder()
                .member(member)
                .message(authMember.getNickname() + "님이 " + notice.getTitle() + "에 댓글을 달았어요!")
                .type(NotificationType.NEW_NOTICE)
                .referenceId(noticeId)
                .status(NotificationStatus.UNREAD)
                .build()
            ).toList();


        notificationModuleService.saveAllNotifications(notifications);
        return CommentConverter.toAddCommentResult(newComment);
    }
}
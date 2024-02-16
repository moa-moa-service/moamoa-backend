package site.moamoa.backend.service.component.command.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.converter.NoticeConverter;
import site.moamoa.backend.converter.PostImageConverter;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Notification;
import site.moamoa.backend.domain.enums.NotificationStatus;
import site.moamoa.backend.domain.enums.NotificationType;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.service.module.member_post.MemberPostModuleService;
import site.moamoa.backend.service.module.notice.NoticeModuleService;
import site.moamoa.backend.service.module.notification.NotificationModuleService;
import site.moamoa.backend.service.module.post.PostModuleService;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO.AddNotice;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.AddNoticeResult;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeCommandServiceImpl implements NoticeCommandService{

    private final NoticeModuleService noticeModuleService;
    private final PostModuleService postModuleService;
    private final MemberPostModuleService memberPostModuleService;
    private final NotificationModuleService notificationModuleService;
    private final AmazonS3Manager amazonS3Manager;

    // 공지사항 등록(업데이트 알림 발송)
    @Override
    public AddNoticeResult registerNotice(Long memberId, Long postId, AddNotice addNotice, MultipartFile image){
        String imageUrl = PostImageConverter.toImageUrl(image, amazonS3Manager);

        Notice newNotice = NoticeConverter.toNotice(addNotice, imageUrl);
        newNotice.setPost(postModuleService.findPostById(postId));

        noticeModuleService.saveNotice(newNotice);

        // 알림(공지사항 등록) 생성
        List<Notification> notifications = memberPostModuleService.findParticipatingMembersByPostId(postId).stream().map(member ->
                        Notification.builder()
                                .status(NotificationStatus.UNREAD)
                                .type(NotificationType.NEW_NOTICE)
                                .message("참여한 " + postModuleService.findPostById(postId).getProductName() + "의 공동구매 공지사항 업데이트!")
                                .referenceId(newNotice.getId())
                                .member(member)
                                .build())
                .toList();
        notificationModuleService.saveAllNotifications(notifications);

        return NoticeConverter.toAddNoticeResult(newNotice);
    }

    // 공지사항 삭제
    @Override
    public NoticeResponseDTO.DeleteNoticeResult deleteNotice(Long postId, Long noticeId) {
        noticeModuleService.deleteNotice(noticeId);
        return NoticeConverter.toDeleteNoticeResult(noticeId, LocalDateTime.now());
    }

    //공지사항 수정(업데이트 알림 발송)
    @Override
    public NoticeResponseDTO.UpdateNoticeResult updateNotice(Long postId, Long noticeId, NoticeRequestDTO.UpdateNotice updateNotice, MultipartFile image) {
        String imageUrl;
        if (!image.isEmpty()) {
            imageUrl = PostImageConverter.toImageUrl(image, amazonS3Manager);
        } else
            imageUrl = null;
        Notice updatedNotice = noticeModuleService.updateNotice(noticeId, updateNotice, imageUrl);

        // 알림(공지사항 등록) 생성
        List<Notification> notifications = memberPostModuleService.findParticipatingMembersByPostId(postId).stream().map(member ->
                        Notification.builder()
                                .status(NotificationStatus.UNREAD)
                                .type(NotificationType.NEW_NOTICE)
                                .message("참여한 " + postModuleService.findPostById(postId).getProductName() + "의 공동구매 공지사항 업데이트!")
                                .referenceId(noticeId)
                                .member(member)
                                .build())
                .toList();
        notificationModuleService.saveAllNotifications(notifications);

        return NoticeConverter.toUpdateNoticeResult(updatedNotice);
    }
}

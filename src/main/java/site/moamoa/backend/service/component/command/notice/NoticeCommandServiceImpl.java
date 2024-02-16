package site.moamoa.backend.service.component.command.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.converter.NoticeConverter;
import site.moamoa.backend.converter.NotificationConverter;
import site.moamoa.backend.converter.PostImageConverter;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Notification;
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
public class NoticeCommandServiceImpl implements NoticeCommandService {

    private final NoticeModuleService noticeModuleService;
    private final PostModuleService postModuleService;
    private final MemberPostModuleService memberPostModuleService;
    private final NotificationModuleService notificationModuleService;
    private final AmazonS3Manager amazonS3Manager;

    // 공지사항 등록(업데이트 알림 발송)
    @Override
    public AddNoticeResult registerNotice(Long memberId, Long postId, AddNotice addNotice, MultipartFile image) {
        String imageUrl = image != null ? PostImageConverter.toImageUrl(image, amazonS3Manager) : null;

        Notice newNotice = NoticeConverter.toNotice(addNotice, imageUrl);
        newNotice.setPost(postModuleService.findPostById(postId));

        noticeModuleService.saveNotice(newNotice);

        String productName = postModuleService.findPostById(postId).getProductName();

        // 알림(공지사항 등록) 생성
        createNotificationForNoticeUpdate(postId, newNotice.getId(), productName);

        return NoticeConverter.toAddNoticeResult(newNotice);
    }

    // 공지사항 삭제
    @Override
    public NoticeResponseDTO.DeleteNoticeResult deleteNotice(Long noticeId) {
        noticeModuleService.deleteNotice(noticeId);
        return NoticeConverter.toDeleteNoticeResult(noticeId, LocalDateTime.now());
    }

    //공지사항 수정(업데이트 알림 발송)
    @Override
    public NoticeResponseDTO.UpdateNoticeResult updateNotice(Long postId, Long noticeId, NoticeRequestDTO.UpdateNotice updateNotice, MultipartFile image) {
        String imageUrl = image != null ? PostImageConverter.toImageUrl(image, amazonS3Manager) : null;

        Notice updatedNotice = noticeModuleService.updateNotice(noticeId, updateNotice, imageUrl);

        String productName = postModuleService.findPostById(postId).getProductName();

        // 알림(공지사항 등록) 생성
        createNotificationForNoticeUpdate(postId, noticeId, productName);

        return NoticeConverter.toUpdateNoticeResult(updatedNotice, LocalDateTime.now());
    }

    private void createNotificationForNoticeUpdate(Long postId, Long noticeId, String productName) {
        List<Notification> notificationList = memberPostModuleService.findParticipatingMembersByPostId(postId).stream().map(member -> {
            Notification notification = NotificationConverter.toNotification("참여한 " + productName + "의 공동구매 공지사항 업데이트!",
                    NotificationType.NEW_NOTICE, noticeId);
            notification.setMember(member);
            return notification;
        }).toList();
        notificationModuleService.saveAllNotifications(notificationList);
    }
}

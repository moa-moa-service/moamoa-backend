package site.moamoa.backend.service.module.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.NoticeHandler;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.repository.notice.NoticeRepository;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NoticeModuleServiceImpl implements NoticeModuleService {

    private final NoticeRepository noticeRepository;

    @Override
    public Notice saveNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public Notice findNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
            .orElseThrow(() -> new NoticeHandler(ErrorStatus.NOTICE_NOT_FOUND));
    }

    @Override
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    @Override
    public Notice updateNotice(Long noticeId, NoticeRequestDTO.UpdateNotice updateNotice, String imageUrl) {
        Notice currentNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NoticeHandler(ErrorStatus.NOTICE_NOT_FOUND));

        currentNotice.updateNotice(updateNotice);

        if (imageUrl != null) {
            currentNotice.changeImage(imageUrl);
        }

        return currentNotice;
    }

    @Override
    public void nullifyPostInNotices(List<Long> noticeIds) {
        noticeRepository.nullifyPostInNotices(noticeIds);
    }
}

package site.moamoa.backend.service.module.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.NoticeHandler;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.repository.notice.NoticeRepository;

@Service
@RequiredArgsConstructor
public class NoticeModuleServiceImpl implements NoticeModuleService {

    private final NoticeRepository noticeRepository;

    @Override
    public Notice findNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
            .orElseThrow(() -> new NoticeHandler(ErrorStatus.NOTICE_NOT_FOUND));
    }
}

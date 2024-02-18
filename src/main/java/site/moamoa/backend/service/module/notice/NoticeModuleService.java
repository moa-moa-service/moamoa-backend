package site.moamoa.backend.service.module.notice;

import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;

import java.util.List;

public interface NoticeModuleService {
    Notice saveNotice(Notice notice);
    Notice findNoticeById(Long noticeId);
    void deleteNotice(Long noticeId);
    Notice updateNotice(Long noticeId, NoticeRequestDTO.UpdateNotice updateNotice, String imageUrl);

    void nullifyPostInNotices(List<Long> noticeIds);
}

package site.moamoa.backend.service.module.notice;

import site.moamoa.backend.domain.Notice;

public interface NoticeModuleService {
    Notice findNoticeById(Long noticeId);
}

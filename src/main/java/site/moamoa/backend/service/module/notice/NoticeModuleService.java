package site.moamoa.backend.service.module.notice;

import site.moamoa.backend.domain.Notice;

public interface NoticeModuleService {
    public Notice saveNotice(Notice notice);
    Notice findNoticeById(Long noticeId);

}

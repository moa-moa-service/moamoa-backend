package site.moamoa.backend.service.module.notice;

import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.repository.notice.NoticeRepository;


public interface NoticeModuleService {
    public Notice saveNotice(Notice notice);
}

package site.moamoa.backend.service.component.query.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.service.module.notice.NoticeModuleService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private final NoticeModuleService noticeModuleService;
}

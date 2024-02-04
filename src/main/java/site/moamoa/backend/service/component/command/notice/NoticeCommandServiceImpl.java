package site.moamoa.backend.service.component.command.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.service.module.notice.NoticeModuleService;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeCommandServiceImpl implements NoticeCommandService{

    private final NoticeModuleService noticeModuleService;
}

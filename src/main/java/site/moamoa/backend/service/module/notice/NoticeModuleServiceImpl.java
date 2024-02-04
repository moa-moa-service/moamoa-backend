package site.moamoa.backend.service.module.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.repository.notice.NoticeRepository;

@Service
@RequiredArgsConstructor
public class NoticeModuleServiceImpl implements NoticeModuleService {

    private final NoticeRepository noticeRepository;
}

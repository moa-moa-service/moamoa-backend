package site.moamoa.backend.service.component.query.notice;

import site.moamoa.backend.web.dto.response.NoticeResponseDTO.GetNotice;

public interface NoticeQueryService {

  GetNotice findNoticeById(Long noticeId);
}

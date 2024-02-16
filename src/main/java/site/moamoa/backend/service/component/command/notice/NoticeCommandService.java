package site.moamoa.backend.service.component.command.notice;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO.AddNotice;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.AddNoticeResult;

public interface NoticeCommandService {

    AddNoticeResult registerNotice(Long memberId, Long postId, AddNotice addNotice, MultipartFile image);
    NoticeResponseDTO.DeleteNoticeResult deleteNotice(Long postId, Long noticeId);
    NoticeResponseDTO.UpdateNoticeResult updateNotice(Long postId, Long noticeId, NoticeRequestDTO.UpdateNotice request, MultipartFile image);
}

package site.moamoa.backend.service.component.command.notice;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO.AddNotice;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.AddNoticeResult;

import java.util.List;

public interface NoticeCommandService {

    public AddNoticeResult registerNotice(Long memberId, Long postId, AddNotice addNotice, MultipartFile image);
}

package site.moamoa.backend.service.component.command.notice;

import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;
import site.moamoa.backend.web.dto.request.PostRequestDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;

public interface NoticeCommandService {

    public NoticeResponseDTO.AddNoticeResult registerNotice(Long memberId, Long postId, NoticeRequestDTO.AddNotice addNotice, MultipartFile image);
}

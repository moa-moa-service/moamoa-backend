package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

public class NoticeConverter {
    public static Notice toNotice(NoticeRequestDTO.AddNotice addNotice, String imageUrl) {
        return Notice.builder()
                .content(addNotice.content())
                .title(addNotice.title())
                .imageUrl(imageUrl)
                .build();
    }

    public static NoticeResponseDTO.AddNoticeResult toAddNoticeResult(Notice notice) {
        return NoticeResponseDTO.AddNoticeResult.builder()
                .noticeId(notice.getId())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}

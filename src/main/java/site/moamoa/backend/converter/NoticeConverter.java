package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class NoticeConverter {
    public static List<NoticeResponseDTO.GetSimpleNotice> toSimpleNoticeDtoList(List<Notice> noticeList) {
        return noticeList.stream().map(NoticeConverter::toSimpleNoticeDTO)
                .collect(Collectors.toList());
    }

    private static NoticeResponseDTO.GetSimpleNotice toSimpleNoticeDTO(Notice notice) {
        return NoticeResponseDTO.GetSimpleNotice.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}

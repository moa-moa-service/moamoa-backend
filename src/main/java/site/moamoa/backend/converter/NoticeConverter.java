package site.moamoa.backend.converter;

import org.springframework.cglib.core.Local;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.web.dto.base.NoticeDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.GetNotice;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.AddNoticeResult;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO.AddNotice;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO;
import static site.moamoa.backend.converter.CommentConverter.toCommentDTOList;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NoticeConverter {
    public static Notice toNotice(AddNotice addNotice, String imageUrl) {
        return Notice.builder()
                .content(addNotice.content())
                .title(addNotice.title())
                .imageUrl(imageUrl)
                .build();
    }

    public static AddNoticeResult toAddNoticeResult(Notice notice) {
        return AddNoticeResult.builder()
                .noticeId(notice.getId())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static NoticeDTO toNoticeDTO(Notice notice) {
        return NoticeDTO.builder()
                .noticeId(notice.getId())
                .postId(notice.getPost().getId())
                .title(notice.getTitle())
                .imageUrl(notice.getImageUrl())
                .content(notice.getContent())
                .commentDTOList(toCommentDTOList(notice.getCommentList()))
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static GetNotice toGetNotice(NoticeDTO noticeDTO, Member member) {
        return GetNotice.builder()
                .noticeDTO(noticeDTO)
                .author(member.getNickname())
                .build();
    }

    public static List<NoticeResponseDTO.GetSimpleNotice> toSimpleNoticeDtoList(List<Notice> noticeList) {
        if (noticeList.isEmpty()) {
            return Collections.emptyList();
        }
        return noticeList.stream().map(NoticeConverter::toSimpleNoticeDTO)
                .collect(Collectors.toList());
    }

    public static NoticeResponseDTO.DeleteNoticeResult toDeleteNoticeResult(Long noticeId, LocalDateTime deletedAt) {
        return NoticeResponseDTO.DeleteNoticeResult.builder()
                .noticeId(noticeId)
                .deletedAt(deletedAt)
                .build();
    }

    public static NoticeResponseDTO.UpdateNoticeResult toUpdateNoticeResult(Notice updatedNotice, LocalDateTime updatedAt) {
        return NoticeResponseDTO.UpdateNoticeResult.builder()
                .noticeId(updatedNotice.getId())
                .updatedAt(updatedAt)
                .build();
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

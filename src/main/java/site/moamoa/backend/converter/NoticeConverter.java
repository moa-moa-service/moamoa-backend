package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.web.dto.base.NoticeDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.GetNotice;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.AddNoticeResult;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO.AddNotice;
import static site.moamoa.backend.converter.CommentConverter.toCommentDTOList;

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
}

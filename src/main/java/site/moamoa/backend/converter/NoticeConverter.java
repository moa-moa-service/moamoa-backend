package site.moamoa.backend.converter;

import static site.moamoa.backend.converter.CommentConverter.toCommentDTOList;

import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.web.dto.base.NoticeDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.GetNotice;
public class NoticeConverter {

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

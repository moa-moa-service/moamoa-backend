package site.moamoa.backend.service.component.query.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.NoticeConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.service.module.member_post.MemberPostModuleServiceImpl;
import site.moamoa.backend.service.module.notice.NoticeModuleService;
import site.moamoa.backend.web.dto.base.NoticeDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO.GetNotice;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements NoticeQueryService {

    private final NoticeModuleService noticeModuleService;
    private final MemberPostModuleServiceImpl memberPostModuleServiceImpl;

    @Override
    public GetNotice findNoticeById(Long noticeId) {
        Notice notice = noticeModuleService.findNoticeById(noticeId);
        NoticeDTO noticeDTO = NoticeConverter.toNoticeDTO(notice);
        Post post = notice.getPost();
        Member author = memberPostModuleServiceImpl.findMemberPostByPostIdAndIsAuthor(post.getId());
        return NoticeConverter.toGetNotice(noticeDTO, author);
    }
}

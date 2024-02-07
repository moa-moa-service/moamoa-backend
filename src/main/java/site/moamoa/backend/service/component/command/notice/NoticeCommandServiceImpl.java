package site.moamoa.backend.service.component.command.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.converter.NoticeConverter;
import site.moamoa.backend.converter.PostImageConverter;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.service.module.notice.NoticeModuleService;
import site.moamoa.backend.service.module.post.PostModuleService;
import site.moamoa.backend.web.dto.request.NoticeRequestDTO;
import site.moamoa.backend.web.dto.response.NoticeResponseDTO;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeCommandServiceImpl implements NoticeCommandService{

    private final NoticeModuleService noticeModuleService;
    private final PostModuleService postModuleService;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public NoticeResponseDTO.AddNoticeResult registerNotice(Long memberId, Long postId, NoticeRequestDTO.AddNotice addNotice, MultipartFile image){
        String imageUrl = PostImageConverter.toImageUrl(image, amazonS3Manager);

        Notice newNotice = NoticeConverter.toNotice(addNotice, imageUrl);
        newNotice.setPost(postModuleService.findPostById(postId));

        noticeModuleService.saveNotice(newNotice);

        return NoticeConverter.toAddNoticeResult(newNotice);
    }
}

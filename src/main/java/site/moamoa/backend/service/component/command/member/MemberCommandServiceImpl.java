package site.moamoa.backend.service.component.command.member;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.domain.Comment;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.DeletionStatus;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.global.oauth2.CustomOAuth2User;
import site.moamoa.backend.service.module.comment.CommentModuleService;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.service.module.member_post.MemberPostModuleService;
import site.moamoa.backend.service.module.notice.NoticeModuleService;
import site.moamoa.backend.service.module.notification.NotificationModuleService;
import site.moamoa.backend.service.module.post.PostModuleService;
import site.moamoa.backend.service.module.post_image.PostImageModuleService;
import site.moamoa.backend.web.dto.request.MemberRequestDTO;
import site.moamoa.backend.web.dto.response.MemberResponseDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    @Value("${default.profileImage.url}")
    private String defaultImageUrl;

    private final MemberModuleService memberModuleService;
    private final MemberPostModuleService memberPostModuleService;
    private final NotificationModuleService notificationModuleService;
    private final PostImageModuleService postImageModuleService;
    private final NoticeModuleService noticeModuleService;
    private final PostModuleService postModuleService;
    private final CommentModuleService commentModuleService;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public MemberResponseDTO.UpdateMemberImageResult addMemberProfileImage(Long memberId, MultipartFile updateMemberImageDto) {
        Member member = memberModuleService.findMemberById(memberId);
        String memberProfileUrl = defaultImageUrl;

        if (updateMemberImageDto != null) {
            memberProfileUrl = amazonS3Manager.uploadImage(amazonS3Manager.generateMemberProfileKeyName(), updateMemberImageDto);
        }

        String profileImage = member.getProfileImage();
        if (!profileImage.equals(defaultImageUrl)) {
            String key = amazonS3Manager.extractImageNameFromUrl(profileImage);
            amazonS3Manager.deleteImage(key);
        }

        member.addProfileImage(memberProfileUrl);
        return MemberConverter.toUpdateMemberImageDTO(member);
    }

    @Override
    public MemberResponseDTO.UpdateMemberAddressResult updateMemberAddress(Long memberId, MemberRequestDTO.UpdateMemberAddress request) {
        Member member = memberModuleService.findMemberById(memberId);
        member.addInfo(member.getNickname(), request.town(), request.address());
        return MemberConverter.toUpdateMemberAddressResult(member);
    }

    @Override
    public MemberResponseDTO.DeleteMemberResult deActiveMemberResult(Long memberId) {
        Member member = memberModuleService.findMemberById(memberId);
        member.deactivate();
        return MemberConverter.toDeActiveMemberResult(member);
    }

    @Override
    public void memberSetRefreshToken(CustomOAuth2User oAuth2User, String refreshToken) {
        Member member = memberModuleService.findMemberById(oAuth2User.getId());
        member.addRefreshToken(refreshToken);
    }

    @Override
    public MemberResponseDTO.LogoutInfo memberDeleteRefreshToken(Long memberId) {
        Member member = memberModuleService.findMemberById(memberId);
        member.addRefreshToken(null);
        return MemberConverter.toLogoutMemberInfoResult(member);
    }

    @Override
    public MemberResponseDTO.AddMemberInfoResult addMemberInfo(Long memberId, MemberRequestDTO.AddMemberInfo memberInfo) {
        Member member = memberModuleService.findMemberById(memberId);
        if (member.getNickname() != null) {
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXISTS);
        }
        member.addInfo(memberInfo.nickname(), memberInfo.town(), memberInfo.address());
        return MemberConverter.toAddMemberInfoResult(member);
    }

    @Scheduled(cron = "0 0 0 1 * ?") //초 분 시 일 월
    public void deleteBySoftDeletedMembers() {
        //열려있는 공동구매가 있다면 회원탈퇴 되도록
        List<Long> memberIds = memberModuleService.findMembersToSoftDelete(DeletionStatus.DELETE)
                .stream().map(Member::getId).collect(Collectors.toList()); // deletionStatus == DELETE 인 멤버 추출 및 삭제 대상 ID 추출

        List<Post> posts = postModuleService.selectPostsInMemberPostByMemberIdsAndIsAuthor(memberIds);
        List<Long> postIds = posts.stream().map(Post::getId).toList();
        List<Long> postImageIds = posts.stream()
                .flatMap(
                        post -> post.getPostImages().stream()
                ).map(PostImage::getId).toList();

        List<Notice> notices = posts.stream().flatMap(post -> post.getNoticeList().stream()).toList();

        List<Long> noticeIds = notices.stream().map(Notice::getId).toList();
        List<Long> commentIds = notices.stream().flatMap(notice -> notice.getCommentList().stream()).map(Comment::getId).toList();

        memberPostModuleService.deleteMemberPostsByMemberIdsOrPostIds(memberIds, postIds); // 해당 memberId, postId를 가진 memberPost 삭제

        commentModuleService.deleteCommentsByIdInBatch(commentIds);
        postImageModuleService.deleteAllByIdInBatch(postImageIds);
        noticeModuleService.deleteAllByIdInBatch(noticeIds);
        postModuleService.deleteAllByIdInBatch(postIds);

        notificationModuleService.deleteNotificationsByMemberIds(memberIds);
        memberModuleService.deleteAllByIdInBatch(memberIds);
    }
}

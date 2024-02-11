package site.moamoa.backend.service.component.command.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberPostHandler;
import site.moamoa.backend.converter.MemberPostConverter;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.converter.PostImageConverter;
import site.moamoa.backend.domain.Category;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.service.module.category.CategoryModuleService;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.service.module.member_post.MemberPostModuleService;
import site.moamoa.backend.service.module.post.PostModuleService;
import site.moamoa.backend.service.module.post_image.PostImageModuleService;
import site.moamoa.backend.service.module.redis.RedisModuleService;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.request.PostRequestDTO.UpdatePostInfo;
import site.moamoa.backend.web.dto.response.PostResponseDTO.*;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final CategoryModuleService categoryModuleService;
    private final PostModuleService postModuleService;
    private final MemberModuleService memberModuleService;
    private final MemberPostModuleService memberPostModuleService;
    private final PostImageModuleService postImageModuleService;

    private final RedisModuleService redisModuleService;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public AddPostResult registerPost(Long memberId, AddPost addPost, List<MultipartFile> images) {
        Category category = categoryModuleService.findCategoryById(addPost.categoryId());
        List<PostImage> postImages = PostImageConverter.toPostImages(images, amazonS3Manager);

        Post newPost = PostConverter.toPost(addPost, category, postImages);
        postImages.forEach(postImage -> postImage.setPost(newPost));

        postModuleService.savePost(newPost);

        Member authMember = memberModuleService.findMemberById(memberId);
        MemberPost newMemberPost = MemberPostConverter.toMemberPostAsAuthor(newPost, authMember);

        memberPostModuleService.saveMemberPost(newMemberPost);

        return PostConverter.toAddPostResult(newPost);
    }

    @Override
    public UpdatePostStatusResult updatePostStatus(Long memberId, Long postId) {
        memberPostModuleService.validMemberPostIsAuthor(memberId, postId);
        Post updatePost = postModuleService.findPostById(postId);
        updatePost.updateStatusToFull();
        return PostConverter.toUpdatePostStatusResult(updatePost);
    }

    @Override
    public UpdatePostInfoResult updatePostInfo(Long memberId, UpdatePostInfo updatePostInfo,
                                               List<MultipartFile> images, Long postId) {
        memberPostModuleService.validMemberPostIsAuthor(memberId, postId);
        Post updatePost = postModuleService.findPostById(postId);
        // categoryId가 존재하면 해당 ID로 Category를 찾고, 그렇지 않으면 null
        Category category = Optional.ofNullable(updatePostInfo.categoryId())
                .map(categoryModuleService::findCategoryById)
                .orElse(null);
        postImageModuleService.deletePostImageByPostId(postId);
        List<PostImage> updatedImages = postImageModuleService.setUpdatedImages(images, updatePost);
        updatePost.updateInfo(updatePostInfo, category, updatedImages);
        postModuleService.savePost(updatePost);
        return PostConverter.toUpdatePostInfoResult(updatePost);
    }

    @Override
    public AddMemberPostResult joinPost(Long memberId, Long postId) {
        Member authMember = memberModuleService.findMemberById(memberId);
        Post joinPost = postModuleService.findPostById(postId);
        if (memberPostModuleService.IsPostAuthor(authMember, joinPost))
            throw new MemberPostHandler(ErrorStatus.AUTHOR_CAN_NOT_BE_PARTICIPATOR);
        MemberPost newMemberPost = MemberPostConverter.toMemberPostAsParticipator(joinPost, authMember);
        memberPostModuleService.saveMemberPost(newMemberPost);
        return MemberPostConverter.toAddMemberPostResult(newMemberPost);
    }

    @Override
    public void updatePostViewCount(Long memberId, Long postId) {
        if (redisModuleService.isNewPostViewRecord(memberId, postId)) {
            Post post = postModuleService.findPostById(postId);
            post.updateViewCount();
        }
    }

    @Override
    public DeleteMemberPostResult cancelPost(Long id, Long postId) {
        MemberPost canceledMemberPost = memberPostModuleService.findMemberPostByPostIdAndMemberId(id, postId);
        DeleteMemberPostResult result = MemberPostConverter.toDeleteMemberPostResult(canceledMemberPost);
        memberPostModuleService.deleteMemberPost(canceledMemberPost.getId());
        return result;
    }
}

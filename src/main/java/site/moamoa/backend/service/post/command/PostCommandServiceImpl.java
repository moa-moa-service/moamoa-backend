package site.moamoa.backend.service.post.command;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.Category;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.converter.member.MemberPostConverter;
import site.moamoa.backend.converter.post.PostConverter;
import site.moamoa.backend.converter.postimage.PostImageConverter;
import site.moamoa.backend.service.category.query.CategoryQueryService;
import site.moamoa.backend.service.member.query.MemberQueryService;
import site.moamoa.backend.service.memberpost.command.MemberPostCommandService;
import site.moamoa.backend.service.memberpost.query.MemberPostQueryService;
import site.moamoa.backend.service.postimage.command.PostImageCommandService;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.request.PostRequestDTO.UpdatePostInfo;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddMemberPostResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddPostResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.UpdatePostInfoResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.UpdatePostStatusResult;
import site.moamoa.backend.exception.post.PostNotFoundException;
import site.moamoa.backend.repository.post.PostRepository;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;
    private final CategoryQueryService categoryQueryService;
    private final PostImageConverter postImageConverter;
    private final MemberQueryService memberQueryService;
    private final MemberPostCommandService memberPostCommandService;
    private final MemberPostQueryService memberPostQueryService;
    private final PostImageCommandService postImageCommandService;

    @Override
    public AddPostResult registerPost(AuthInfoDTO auth, AddPost addPost, List<MultipartFile> images) {
        Category category = categoryQueryService.findCategoryById(addPost.categoryId());
        List<PostImage> postImages = postImageConverter.toPostImages(images);
        Post newPost = PostConverter.toPost(addPost, category, postImages);
        postImages
            .forEach(postImage -> postImage.setPost(newPost));
        postRepository.save(newPost);
        Member authMember = memberQueryService.findMemberById(auth.id());
        MemberPost newMemberPost = MemberPostConverter.toMemberPostAsAuthor(newPost, authMember);
        memberPostCommandService.save(newMemberPost);
        return PostConverter.toAddPostResult(newPost);
    }

    @Override
    public UpdatePostStatusResult updatePostStatus(AuthInfoDTO auth, Long postId) {
        memberPostQueryService.checkAuthor(auth.id(), postId);
        updatePostStatusToFull(postId);
        Post updatedPost = findPostById(postId);
        return PostConverter.toUpdatePostStatusResult(updatedPost);
    }

    @Override
    public UpdatePostInfoResult updatePostInfo(AuthInfoDTO auth, UpdatePostInfo updatePostInfo,
        List<MultipartFile> images, Long postId) {
        memberPostQueryService.checkAuthor(auth.id(), postId);
        Post updatePost = findPostById(postId);
        Category category = categoryQueryService.findCategoryById(updatePostInfo.categoryId());
        postImageCommandService.deletePostImageByPostId(postId);
        List<PostImage> updatedImages = postImageConverter.toPostImages(images);
        updatedImages
            .forEach(postImage -> postImage.setPost(updatePost));
        updatePost.updateInfo(updatePostInfo, category, updatedImages);
        postRepository.save(updatePost);
        return PostConverter.toUpdatePostInfoResult(updatePost);
    }

    @Override
    public AddMemberPostResult joinPost(AuthInfoDTO auth, Long postId) {
        Member authMember = memberQueryService.findMemberById(auth.id());
        Post joinPost = findPostById(postId);
        MemberPost newMemberPost = MemberPostConverter.toMemberPostAsParticipator(joinPost, authMember);
        memberPostCommandService.save(newMemberPost);
        return MemberPostConverter.toAddMemberPostResult(newMemberPost);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException()
            );
    }

    private void updatePostStatusToFull(Long postId) {
        postRepository.updateStatusToFull(postId, CapacityStatus.NOT_FULL, CapacityStatus.FULL);
    }
}

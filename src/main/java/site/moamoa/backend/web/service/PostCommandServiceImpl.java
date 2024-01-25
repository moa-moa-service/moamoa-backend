package site.moamoa.backend.web.service;

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
import site.moamoa.backend.web.converter.MemberPostConverter;
import site.moamoa.backend.web.converter.PostConverter;
import site.moamoa.backend.web.converter.PostImageConverter;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddPostResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.UpdatePostStatusResult;
import site.moamoa.backend.web.exception.PostNotFoundException;
import site.moamoa.backend.web.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;
    private final CategoryQueryService categoryQueryService;
    private final PostImageConverter postImageConverter;
    private final MemberQueryService memberQueryService;
    private final MemberPostCommandService memberPostCommandService;
    private final MemberPostQueryService memberPostQueryService;

    @Override
    public AddPostResult registerPost(AuthInfoDTO auth, AddPost addPost, List<MultipartFile> images) {
        Category category = categoryQueryService.findCategoryById(addPost.categoryId());
        List<PostImage> postImages = postImageConverter.toPostImages(images);
        Post newPost = PostConverter.toPost(addPost, category, postImages);
        postImages
            .forEach(postImage -> postImage.setPost(newPost));
        postRepository.save(newPost);
        Member authMember = memberQueryService.findMemberById(auth.id());
        MemberPost newMemberPost = MemberPostConverter.toMemberPost(newPost, authMember);
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

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException()
            );
    }

    private void updatePostStatusToFull(Long postId) {
        postRepository.updateStatusToFull(postId, CapacityStatus.NOT_FULL, CapacityStatus.FULL);
    }
}

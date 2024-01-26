package site.moamoa.backend.service.post.command;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.config.redis.RedisKey;
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
@Transactional
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;
    private final CategoryQueryService categoryQueryService;
    private final PostImageConverter postImageConverter;
    private final MemberQueryService memberQueryService;
    private final MemberPostCommandService memberPostCommandService;
    private final MemberPostQueryService memberPostQueryService;
    private final PostImageCommandService postImageCommandService;
    private final RedisTemplate<String, Object> redisTemplate;

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

    @Override
    public void updateKeywordCount(Long memberId, String keyword) {
        redisTemplate.opsForZSet()
            .add(RedisKey.MEMBER_KEYWORD_KEY_PREFIX + memberId, keyword, LocalDateTime.now().toEpochSecond(
                ZoneOffset.UTC));

        String town = memberQueryService.findMemberById(memberId).getTown();
        redisTemplate.opsForZSet().addIfAbsent(RedisKey.TOWN_KEYWORD_COUNT_KEY_PREFIX + town, keyword, 0);
        redisTemplate.opsForZSet().incrementScore(RedisKey.TOWN_KEYWORD_COUNT_KEY_PREFIX + town, keyword, 1);
    }

    @Override
    public void updatePostViewCount(Long memberId, Long postId) {
        String key = buildPostViewKey(memberId, postId);

        // 이미 조회한 경우 무시
        if (isNewViewRecord(memberId, postId)) {
            saveViewRecord(key);
            updatePostView(postId);
        }
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException()
            );
    }

    private void updatePostStatusToFull(Long postId) {
        postRepository.updateStatusToFull(postId, CapacityStatus.NOT_FULL, CapacityStatus.FULL);
    }

    private String buildPostViewKey(Long memberId, Long postId) {
        return RedisKey.POST_VIEW_KEY_PREFIX + memberId + ":" + postId;
    }

    private boolean isNewViewRecord(Long memberId, Long postId) {
        return Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(buildPostViewKey(memberId, postId), postId));
    }

    private void saveViewRecord(String key) {
        redisTemplate.opsForSet().add(key, true);
        redisTemplate.expire(key, Duration.ofSeconds(RedisKey.EXPIRATION_VIEW_RECORD));
    }

    private void updatePostView(Long postId) {
        if (postRepository.updateView(postId) != 1) {
            throw new PostHandler(ErrorStatus.POST_NOT_FOUND);
        }
    }

}

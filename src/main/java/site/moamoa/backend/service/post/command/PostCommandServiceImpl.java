package site.moamoa.backend.service.post.command;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.PostHandler;
import site.moamoa.backend.config.redis.RedisKey;
import site.moamoa.backend.converter.member.MemberPostConverter;
import site.moamoa.backend.converter.post.PostConverter;
import site.moamoa.backend.converter.postimage.PostImageConverter;
import site.moamoa.backend.domain.Category;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.repository.post.PostRepository;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


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

    private static final Long RECENT_KEYWORD_SIZE = 10L;

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
        Post updatePost = findPostById(postId);
        updatePost.updateStatusToFull();
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
        String memberKey = RedisKey.MEMBER_KEYWORD_KEY_PREFIX + memberId;
        redisTemplate.opsForZSet()
                .add(memberKey, keyword, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        Long size = redisTemplate.opsForZSet().size(memberKey);
        if (size > RECENT_KEYWORD_SIZE) {
            redisTemplate.opsForZSet().popMin(memberKey);
        }

        String town = memberQueryService.findMemberById(memberId).getTown();
        String townKey = RedisKey.TOWN_KEYWORD_COUNT_KEY_PREFIX + town;
        redisTemplate.opsForZSet().addIfAbsent(townKey, keyword, 0);
        redisTemplate.opsForZSet().incrementScore(townKey, keyword, 1);
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

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostHandler(ErrorStatus.POST_NOT_FOUND)
            );
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

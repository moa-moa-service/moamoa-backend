package site.moamoa.backend.service.component.query.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.converter.NoticeConverter;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.service.module.member_post.MemberPostModuleService;
import site.moamoa.backend.service.module.post.PostModuleService;
import site.moamoa.backend.service.module.redis.RedisModuleService;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostModuleService postModuleService;
    private final MemberModuleService memberModuleService;
    private final MemberPostModuleService memberPostModuleService;

    private final RedisModuleService redisModuleService;

    @Override
    public PostResponseDTO.GetPosts findPostsByNear(Long memberId) {
        Address address = memberModuleService.findMemberById(memberId).getAddress();
        List<Post> posts = postModuleService.findPostsByNear(address.getLatitude(), address.getLatitude());

        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByLatest() {
        List<Post> posts = postModuleService.findPostsByRecent();

        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByRanking(Long memberId) {
        Member member = memberModuleService.findMemberById(memberId);
        List<Post> posts = postModuleService.findPostsByRanking(member.getTown());

        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByRecentKeyword(Long memberId) {
        String keyword = redisModuleService.getKeywordByMemberRecentFirst(memberId);
        List<Post> posts = new ArrayList<>();
        if (keyword != null) {
            posts = postModuleService.findPostsByKeyword(keyword);
        }

        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPosts findPostsByConditions(String keyword, Long categoryId, Integer dDay, Integer total, Integer minPrice, Integer maxPrice) {
        List<Post> posts = postModuleService.findPostsByKeyword(keyword, categoryId, dDay, total, minPrice, maxPrice);

        return PostConverter.toGetPosts(
                posts.stream().map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages())).toList()
        );
    }

    @Override
    public PostResponseDTO.GetPost fetchDetailedPostByPostId(Long memberId, Long postId) {
        MemberPost fetchedMemberPost = memberPostModuleService.fetchDetailedPostByPostId(postId);
        Post fetchedPost = fetchedMemberPost.getPost();
        Member fetchedMember = fetchedMemberPost.getMember();

        Boolean isMemberPost = memberPostModuleService.existsMemberPostByMemberIdAndPostId(memberId, postId);

        IsAuthorStatus joinStatus = determineJoinStatus(memberId, fetchedMember, isMemberPost);

        return PostConverter.toGetPost(
                PostConverter.toPostDTO(fetchedPost, fetchedPost.getPostImages(), fetchedPost.getCategory()),
                MemberConverter.toMemberDTO(fetchedMember),
                joinStatus,
                NoticeConverter.toSimpleNoticeDtoList(isMemberPost ? fetchedMemberPost.getPost().getNoticeList() : Collections.emptyList())
        );
    }

    private IsAuthorStatus determineJoinStatus(Long memberId, Member fetchedMember, Boolean isMemberPost) {
        if (fetchedMember.getId().equals(memberId)) {
            return IsAuthorStatus.AUTHOR;
        }
        if (isMemberPost) {
            return IsAuthorStatus.PARTICIPATOR;
        }
        return null;
    }
}

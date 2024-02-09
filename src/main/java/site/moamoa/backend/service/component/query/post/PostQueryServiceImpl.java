package site.moamoa.backend.service.component.query.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.MemberConverter;
import site.moamoa.backend.converter.NoticeConverter;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Member;
import site.moamoa.backend.domain.Notice;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.embedded.Address;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.service.module.member.MemberModuleService;
import site.moamoa.backend.service.module.member_post.MemberPostModuleService;
import site.moamoa.backend.service.module.post.PostModuleService;
import site.moamoa.backend.service.module.redis.RedisModuleService;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public PostResponseDTO.GetPost findPostById(Long memberId, Long postId) {
        Post post = postModuleService.findPostById(postId);
        Member admin = memberPostModuleService.findMemberPostByPostIdAndIsAuthor(postId);
        List<Notice> noticeList = Collections.emptyList();
        Optional<MemberPost> memberPostByPostIdAndMemberId = memberPostModuleService.findMemberPostByMemberIdAndPostId(memberId, postId);

        if (memberPostByPostIdAndMemberId.isPresent()) {
            noticeList = post.getNoticeList();
        }
        return PostConverter.toGetPost(PostConverter.toPostDTO(post, post.getPostImages(), post.getCategory()), MemberConverter.toMemberDTO(admin), NoticeConverter.toSimpleNoticeDtoList(noticeList));
    }
}

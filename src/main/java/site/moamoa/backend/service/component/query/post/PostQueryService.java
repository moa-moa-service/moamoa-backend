package site.moamoa.backend.service.component.query.post;

import site.moamoa.backend.web.dto.response.PostResponseDTO;

public interface PostQueryService {

    PostResponseDTO.GetPostsWithAddress findPostsByNear(Long memberId);

    PostResponseDTO.GetPosts findPostsByLatest();

    PostResponseDTO.GetPosts findPostsByRanking(Long memberId);

    PostResponseDTO.GetPosts findPostsByRecentKeyword(Long memberId);

    PostResponseDTO.GetPosts findPostsByConditions(String keyword, Long categoryId, Integer dDay, Integer total, Integer minPrice, Integer maxPrice);

    PostResponseDTO.GetPost fetchDetailedPostByPostId(Long memberId, Long postId);
}

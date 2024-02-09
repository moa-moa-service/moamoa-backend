package site.moamoa.backend.service.component.query.post;

import site.moamoa.backend.web.dto.response.PostResponseDTO;

public interface PostQueryService {

    PostResponseDTO.GetPosts findPostsByNear(Long memberId);

    PostResponseDTO.GetPosts findPostsByLatest();

    PostResponseDTO.GetPosts findPostsByRanking(Long memberId);

    PostResponseDTO.GetPosts findPostsByRecentKeyword(Long memberId);

    PostResponseDTO.GetPosts findPostsByConditions(String keyword, Long categoryId, Integer dDay, Integer total, Integer minPrice, Integer maxPrice);

    PostResponseDTO.GetPost findPostById(Long memberId, Long postId);
}

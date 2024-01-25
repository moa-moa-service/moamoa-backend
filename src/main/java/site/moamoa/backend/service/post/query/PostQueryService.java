package site.moamoa.backend.service.post.query;

import site.moamoa.backend.web.dto.base.SimplePostDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.util.List;

public interface PostQueryService {

    PostResponseDTO.GetPosts findPostsByNear(Long memberId);

    PostResponseDTO.GetPosts findPostsByLatest();

    PostResponseDTO.GetPosts findPostsByRanking(Long memberId);

    PostResponseDTO.GetPosts findPostsByRecentKeyword(Long memberId);

    PostResponseDTO.GetPosts findPostsByConditions(String keyword, String category, Integer dDay, Integer total, Integer minPrice, Integer maxPrice);

    PostResponseDTO.GetPost findPostById(Long postId);
}

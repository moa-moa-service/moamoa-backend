package site.moamoa.backend.service.module.post;

import site.moamoa.backend.domain.Post;

import java.util.List;

public interface PostModuleService {
    Post savePost(Post post);

    Post findPostById(Long postId);

    List<Post> findPostsByNear(Double latitude, Double longitude);

    List<Post> findPostsByRecent();

    List<Post> findPostsByRanking(String town);

    List<Post> findPostsByKeyword(String keyword);

    List<Post> findPostsByKeyword(String keyword, Long categoryId, Integer dDay, Integer total, Integer minPrice, Integer maxPrice);
}

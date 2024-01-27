package site.moamoa.backend.repository.post;

import site.moamoa.backend.domain.Post;

import java.util.List;

public interface PostQueryDSLRepository {
    List<Post> findAllByNear(Double latitude, Double longitude);

    List<Post> findAllByRanking(String town);

    List<Post> findAllByRecent();

    List<Post> findAllByKeyword(String keyword);

    List<Post> findAllByKeywordAndCondition(String keyword, Long categoryId, Integer dDay, Integer total, Integer minPrice, Integer maxPrice);

    Long updateView(Long postId);
}

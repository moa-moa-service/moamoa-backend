package site.moamoa.backend.repository.post;

import site.moamoa.backend.domain.Post;

import java.util.List;

public interface PostQueryDSLRepository {
    List<Post> findAllByNear(Double latitude, Double longitude);

    List<Post> findAllByRanking(String town);

    List<Post> findAllByRecent();

    List<Post> findAllByKeyword(String keyword);

    Long updateView(Long postId);
}

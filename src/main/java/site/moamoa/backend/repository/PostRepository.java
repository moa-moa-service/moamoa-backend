package site.moamoa.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.dealTown = :town")
    List<Post> findAllByNear(@Param("town") String town);

    @Query("SELECT p FROM Post p " +
            "ORDER BY " +
            "p.createdAt DESC")
    List<Post> findAllByRecent();

    @Query("SELECT p FROM Post p " +
            "ORDER BY " +
            "p.available ASC, " +
            "p.viewCount DESC")
    List<Post> findAllByRanking();

    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :postId")
    Integer updateView(@Param("postId") Long postId);

}
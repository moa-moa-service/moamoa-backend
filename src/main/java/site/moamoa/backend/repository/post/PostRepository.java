package site.moamoa.backend.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moamoa.backend.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDSLRepository {
    @Modifying
    @Query("DELETE FROM Post p WHERE p.id IN :ids")
    void deletePostsByPostIds(@Param("ids") List<Long> ids);

    @Modifying
    @Query("SELECT p FROM Post p WHERE p.id IN (SELECT mp.post.id FROM MemberPost mp WHERE mp.member.id IN :ids AND mp.isAuthorStatus = 'Author')")
    List<Post> selectPostsInMemberPostByMemberIdsAndIsAuthor(@Param("ids") List<Long> memberIds);
}

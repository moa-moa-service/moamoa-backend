package site.moamoa.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.converter.PostConverter;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.repository.PostRepository;
import site.moamoa.backend.web.dto.base.PostDTO;
import site.moamoa.backend.web.dto.base.SimplePostDTO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final KeywordService keywordService;

    @Transactional
    public Long createPost(PostDTO form){
        Post post = PostConverter.toPost(form);
        postRepository.save(post);

        return post.getId();
    }

    public Optional<Post> findOne(Long id) {
        return postRepository.findById(id);
    }

    public List<SimplePostDTO> searchPostsByKeyword() {
        return null;
    }

    // queryDSL, 로그인 멤버 id 얻어오기 적용 후 위 메서드로 바꾸기
    public List<SimplePostDTO> findByKeyword(Long memberId, String keyword) {
        try {
            //redisTemplate.opsForZSet().addIfAbsent("searchBy::member1", keyword,0);
            redisTemplate.opsForZSet().add("member::" + memberId, keyword, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            log.info("searching time : " + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        List<SimplePostDTO> simplePostDTOS = new ArrayList<>();
        List<Post> posts = postRepository.findByProductNameContaining(keyword);
        for(Post post : posts) {
            simplePostDTOS.add(PostConverter.getPostsDTO(post));
        }
        return simplePostDTOS;
    }
}

package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Post;
import site.moamoa.backend.web.dto.base.PostDTO;
import site.moamoa.backend.web.dto.base.SimplePostDTO;


public class PostConverter {
    public static SimplePostDTO getPostsDTO(Post post){
        return SimplePostDTO.builder()
                .productName(post.getProductName())
                .personnel(post.getPersonnel())
                .build();
    } // 필드 더 추가 해야함!

    public static Post toPost(PostDTO postDTO) {
        return Post.builder()
                .productName(postDTO.productName())
                .personnel(postDTO.personnel())
                .totalPrice(postDTO.price())
                .description(postDTO.description())
                .build();
    }
}

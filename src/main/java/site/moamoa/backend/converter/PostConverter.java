package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Post;
import site.moamoa.backend.web.dto.base.SimplePostDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class PostConverter {

    public static SimplePostDTO toSimplePostDTO(Post post) {

        Integer personnel = post.getPersonnel();

        return SimplePostDTO.builder()
                .imageUrl(post.getPostImages().getFirst().getUrl())
                .productName(post.getProductName())
                .personnel(personnel)
                .price(post.getTotalPrice() / personnel)
                .status(post.getCapacityStatus())
                .dDay(post.getDeadline().getDayOfMonth() - LocalDateTime.now().getDayOfMonth())
                .build();
    }

    public static PostResponseDTO.GetPosts toGetPosts(List<SimplePostDTO> dtoList) {
        return PostResponseDTO.GetPosts.builder()
                .SimplePostDtoList(dtoList)
                .build();
    }

}

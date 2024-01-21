package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.web.dto.base.PostDTO;
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

    public static PostDTO toPostDTO(Post post) {

        Integer personnel = post.getPersonnel();

        return PostDTO.builder()
                .imageUrl(post.getPostImages().stream()
                        .map(PostImage::getUrl)
                        .toList())
                .productName(post.getProductName())
                .personnel(personnel)
                .price(post.getTotalPrice() / personnel)
                .category(post.getCategory().getName())
                .dealLocation(post.getDealLocation())
                .deadline(post.getDeadline())
                .description(post.getDescription())
                .build();
    }

    public static PostResponseDTO.GetPost toGetPost(PostDTO dto) {
        return PostResponseDTO.GetPost.builder()
                .postDto(dto)
                .build();
    }

}

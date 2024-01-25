package site.moamoa.backend.converter;

import site.moamoa.backend.domain.Category;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.web.dto.base.PostDTO;
import site.moamoa.backend.web.dto.base.SimplePostDTO;
import site.moamoa.backend.web.dto.response.PostResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class PostConverter {

    public static SimplePostDTO toSimplePostDTO(Post post, List<PostImage> postImageList) {

        Integer personnel = post.getPersonnel();

        return SimplePostDTO.builder()
                .imageUrl(postImageList.get(0).getUrl())
                .productName(post.getProductName())
                .personnel(personnel)
                .viewCount(post.getViewCount())
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

    public static PostDTO toPostDTO(Post post, List<PostImage> postImageList, Category category) {

        Integer personnel = post.getPersonnel();

        return PostDTO.builder()
                .imageUrl(postImageList.stream()
                        .map(PostImage::getUrl)
                        .toList())
                .productName(post.getProductName())
                .personnel(personnel)
                .viewCount(post.getViewCount())
                .available(post.getAvailable())
                .price(post.getTotalPrice() / personnel)
                .category(category.getName())
                .dealLocation(post.getDealLocation())
                .deadline(post.getDeadline())
                .description(post.getDescription())
                .build();
    }

     public static Post toPost(PostDTO postDTO) {
        return Post.builder()
                .productName(postDTO.productName())
                .personnel(postDTO.personnel())
                .totalPrice(postDTO.price())
                .description(postDTO.description())
                .build();
    }
  
    public static PostResponseDTO.GetPost toGetPost(PostDTO dto) {
        return PostResponseDTO.GetPost.builder()
                .postDto(dto)
                .build();
    }

    public static PostResponseDTO.GetMyPostList toMyParticipatedOrRecruitingPostResult(Long memberId, List<Post> postList) {
        List<SimplePostDTO> simplePostDTOS = postList.stream()
                .map(post -> PostConverter.toSimplePostDTO(post, post.getPostImages()))
                .toList();

        return PostResponseDTO.GetMyPostList.builder()
                .userId(memberId)
                .simplePostDtoList(simplePostDTOS)
                .build();
    }
}

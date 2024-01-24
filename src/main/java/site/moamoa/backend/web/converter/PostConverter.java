package site.moamoa.backend.web.converter;

import java.util.List;
import site.moamoa.backend.domain.Category;
import site.moamoa.backend.domain.Post;
import site.moamoa.backend.domain.enums.CapacityStatus;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddPostResult;

public class PostConverter {
    public static Post toPost(AddPost addPost, Category category){
        return Post.builder()
            .available(addPost.personnel())
            .capacityStatus(CapacityStatus.NOT_FULL)
            .category(category)
            .personnel(addPost.personnel())
            .deadline(addPost.deadline())
            .productName(addPost.productName())
//            .postImages(postImages)
            .dealLocation(addPost.dealLocation())
            .totalPrice(addPost.price())
            .description(addPost.description())
            .build();
    }
//    public static Post toPost(AddPost addPost, Category category, List<PostImage> postImages){
//        return Post.builder()
//            .category(category)
//            .personnel(addPost.personnel())
//            .deadline(addPost.deadline())
//            .productName(addPost.productName())
//            .postImages(postImages)
//            .dealLocation(addPost.dealLocation())
//            .totalPrice(addPost.price())
//            .description(addPost.description())
//            .build();
//    }

    public static AddPostResult toAddPostResult(Post post){
        return AddPostResult.builder()
            .postId(post.getId())
            .createdAt(post.getCreatedAt())
            .build();
    }


}

package site.moamoa.backend.web.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PostResponseDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetPosts {
        Long postId;
        Integer total;
        Integer d_day;
        Integer price;
        CapacityStatus status;
        String keyword;
        String image_url;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class AddPostResult {
        Long postId;
        LocalDateTime createdAt;
    }
}

package site.moamoa.backend.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.moamoa.backend.web.dto.base.PostDTO;
import site.moamoa.backend.web.dto.base.SimplePostDTO;

public class PostResponseDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetPost {
        PostDTO postDto;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetPosts {
        List<PostDTO> postDtoList;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetPostsByKeyword {
        List<SimplePostDTO> simplePostDtoList;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class AddPostResult {
        Long postId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class UpdatePostInfoResult {
        Long postId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class UpdatePostStatusResult {
        Long postId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class AddMemberPostResult {
        Long member_post_id;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetMyPostList {
        Long user_id;
        List<SimplePostDTO> simplePostDtoList;
    }
}

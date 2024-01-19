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
        List<SimplePostDTO> SimplePostDtoList;
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
        Long memberPostId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetMyPostList {
        Long userId;
        List<SimplePostDTO> simplePostDtoList;
    }
}

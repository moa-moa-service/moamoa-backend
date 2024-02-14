package site.moamoa.backend.web.dto.response;

import lombok.Builder;
import site.moamoa.backend.web.dto.base.MemberDTO;
import site.moamoa.backend.web.dto.base.PostDTO;
import site.moamoa.backend.web.dto.base.SimplePostDTO;

import java.time.LocalDateTime;
import java.util.List;

import static site.moamoa.backend.web.dto.response.NoticeResponseDTO.GetSimpleNotice;

public class PostResponseDTO {

    @Builder
    public record GetPost(
            PostDTO postDto,
            MemberDTO adminDto,
            List<GetSimpleNotice> simpleNoticeDtoList
    ) {
    }

    @Builder
    public record GetPosts(
            List<SimplePostDTO> SimplePostDtoList
    ) {
    }

    @Builder
    public record AddPostResult(
            Long postId,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record UpdatePostInfoResult(
            Long postId,
            LocalDateTime updatedAt
    ) {
    }

    @Builder
    public record UpdatePostStatusResult(
            Long postId,
            LocalDateTime updatedAt
    ) {
    }

    @Builder
    public record AddMemberPostResult(
            Long memberPostId,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record DeleteMemberPostResult(
            Long memberPostId,
            LocalDateTime deletedAt
    ) {
    }

    @Builder
    public record GetMyPostList(
            Long userId,
            List<SimplePostDTO> simplePostDtoList
    ) {
    }
}

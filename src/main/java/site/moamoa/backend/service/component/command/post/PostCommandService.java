package site.moamoa.backend.service.component.command.post;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.request.PostRequestDTO.UpdatePostInfo;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddMemberPostResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddPostResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.UpdatePostInfoResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.UpdatePostStatusResult;

public interface PostCommandService {
    AddPostResult registerPost(Long memberId, AddPost addPost, List<MultipartFile> images);

    UpdatePostStatusResult updatePostStatus(Long memberId, Long postId);

    UpdatePostInfoResult updatePostInfo(Long memberId, UpdatePostInfo request, List<MultipartFile> images, Long postId);

    AddMemberPostResult joinPost(Long memberId, Long postId);

    void updatePostViewCount(Long memberId, Long postId);
}

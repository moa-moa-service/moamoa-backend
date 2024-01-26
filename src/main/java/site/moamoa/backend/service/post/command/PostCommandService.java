package site.moamoa.backend.service.post.command;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.request.PostRequestDTO.UpdatePostInfo;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddPostResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.UpdatePostInfoResult;
import site.moamoa.backend.web.dto.response.PostResponseDTO.UpdatePostStatusResult;

public interface PostCommandService {
    AddPostResult registerPost(AuthInfoDTO auth, AddPost addPost, List<MultipartFile> images);

    UpdatePostStatusResult updatePostStatus(AuthInfoDTO auth, Long postId);

    UpdatePostInfoResult updatePostInfo(AuthInfoDTO auth, UpdatePostInfo request, List<MultipartFile> images, Long postId);
}

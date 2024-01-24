package site.moamoa.backend.web.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.web.dto.base.AuthInfoDTO;
import site.moamoa.backend.web.dto.request.PostRequestDTO.AddPost;
import site.moamoa.backend.web.dto.response.PostResponseDTO.AddPostResult;

public interface PostCommandService {
    AddPostResult registerPost(AuthInfoDTO auth, AddPost addPost, List<MultipartFile> images);
}

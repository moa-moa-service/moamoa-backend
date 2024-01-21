package site.moamoa.backend.service.post.query;

import site.moamoa.backend.web.dto.response.PostResponseDTO;

public interface PostQueryService {
    PostResponseDTO.GetPosts findPosts(Long id);
}

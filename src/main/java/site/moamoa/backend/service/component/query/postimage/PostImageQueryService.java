package site.moamoa.backend.service.component.query.postimage;

import java.util.List;
import site.moamoa.backend.domain.mapping.PostImage;

public interface PostImageQueryService {
    List<PostImage> findPostImageByPostId(Long id);
}

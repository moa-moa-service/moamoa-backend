package site.moamoa.backend.service.postimage.query;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.repository.postimage.PostImageRepository;

@Service
@AllArgsConstructor
public class PostImageQueryServiceImpl implements PostImageQueryService {
  private final PostImageRepository postImageRepository;

  @Override
  public List<PostImage> findPostImageByPostId(Long id) {
    return postImageRepository.findByPostId(id);
  }
}

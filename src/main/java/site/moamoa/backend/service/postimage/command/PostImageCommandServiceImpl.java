package site.moamoa.backend.service.postimage.command;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.repository.postimage.PostImageRepository;

@Service
@AllArgsConstructor
public class PostImageCommandServiceImpl implements PostImageCommandService {
  private final PostImageRepository postImageRepository;

  @Override
  public void deletePostImageByPostId(Long id) {
    List<PostImage> postImages = postImageRepository.findByPostId(id);
    postImages.forEach(image -> postImageRepository.delete(image));
  }
}

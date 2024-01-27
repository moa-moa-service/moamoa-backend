package site.moamoa.backend.service.component.command.postimage;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.mapping.PostImage;
import site.moamoa.backend.repository.postimage.PostImageRepository;
import site.moamoa.backend.service.component.command.postimage.PostImageCommandService;

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

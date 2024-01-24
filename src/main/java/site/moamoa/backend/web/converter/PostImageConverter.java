package site.moamoa.backend.web.converter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.aws.s3.AmazonS3Manager;
import site.moamoa.backend.domain.mapping.PostImage;

@AllArgsConstructor
@Component
public class PostImageConverter {
    private final AmazonS3Manager amazonS3Manager;

    public List<PostImage> toPostImages(List<MultipartFile> multipartFiles) {
      return multipartFiles.stream()
          .map(this::convertToFileAndUrl)
          .collect(Collectors.toList());
    }
    private PostImage convertToFileAndUrl(MultipartFile file) {
      String storedImageUrl = saveFileAndGetUrl(file);
      return PostImage.builder()
          .url(storedImageUrl)
          .build();
    }

    private String saveFileAndGetUrl(MultipartFile file) {
      String postKey = amazonS3Manager.generatePostKeyName();
      return amazonS3Manager.uploadImage(postKey, file);
    }

}

package site.moamoa.backend.converter;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.global.aws.s3.AmazonS3Manager;
import site.moamoa.backend.domain.mapping.PostImage;

import java.util.List;

@AllArgsConstructor
public class PostImageConverter {

    public static List<PostImage> toPostImages(List<MultipartFile> files, AmazonS3Manager amazonS3Manager) {
        return files.stream()
                .map(file -> PostImageConverter.convertToFileAndUrl(file, amazonS3Manager))
                .toList();
    }

    private static PostImage convertToFileAndUrl(MultipartFile file, AmazonS3Manager amazonS3Manager) {
        String storedImageUrl = saveFileAndGetUrl(file, amazonS3Manager);
        return PostImage.builder()
                .url(storedImageUrl)
                .build();
    }

    private static String saveFileAndGetUrl(MultipartFile file, AmazonS3Manager amazonS3Manager) {
        String postKey = amazonS3Manager.generatePostKeyName();
        return amazonS3Manager.uploadImage(postKey, file);
    }

}

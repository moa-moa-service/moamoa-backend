package site.moamoa.backend.web.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class MemberResponseDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class AddMemberInfoResult {
        Long member_id;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class GetMyInfoResult {
        String nickname;
        String town_name;
        MultipartFile profile_image;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class UpdatedMemberImageResult {
        Long user_id;
        LocalDateTime updatedAt;
    }

}

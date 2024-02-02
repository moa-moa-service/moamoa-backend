package site.moamoa.backend.web.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import site.moamoa.backend.domain.embedded.Address;

public class MemberRequestDTO {

    public record AddMemberInfo(
            @NotNull
            String nickname,

            @NotNull
            String town,
            @NotNull
            Address address
    ) {
    }

    public record UpdateMemberImage(
            MultipartFile profileImage
    ) {
    }

    public record UpdateMemberAddress(
            String town,
            Address address
    ) {
    }
}

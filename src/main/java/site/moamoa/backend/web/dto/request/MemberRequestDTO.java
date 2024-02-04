package site.moamoa.backend.web.dto.request;

import jakarta.validation.constraints.NotNull;
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

    public record UpdateMemberAddress(
            String town,
            Address address
    ) {
    }
}

package site.moamoa.backend.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

public class MemberReqeustDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class AddMemberInfo {
        @NotNull
        String nickname;
        @NotNull Address location;
    }

}

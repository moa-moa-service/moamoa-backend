package site.moamoa.backend.web.dto;

import site.moamoa.backend.domain.embedded.Address;

public class MemberRequestDTO {
    public record AddMemberInfo(String nickname, Address address) {
    }
}

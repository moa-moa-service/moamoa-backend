package site.moamoa.backend.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    GUEST("ROLE_GUEST"), MEMBER("ROLE_MEMBER");

    private final String key;
}
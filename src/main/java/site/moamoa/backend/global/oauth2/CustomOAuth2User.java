package site.moamoa.backend.global.oauth2;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import site.moamoa.backend.domain.enums.RoleType;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final Long id;
    private final RoleType roleType;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            Long id,
                            RoleType roleType) {
        super(authorities, attributes, nameAttributeKey);
        this.id = id;
        this.roleType = roleType;
    }
}

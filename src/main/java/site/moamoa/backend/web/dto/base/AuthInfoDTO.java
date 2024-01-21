package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.moamoa.backend.domain.enums.RoleType;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record AuthInfoDTO(
        Long id,
        String username,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {
    @Override
    public Long id() {
        return id;
    }

    @Builder
    public AuthInfoDTO {
        if (Objects.isNull(authorities)) {
            authorities = Set.of(RoleType.GUEST).stream()
                    .map(RoleType::getKey)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toUnmodifiableSet());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


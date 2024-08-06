package tamlagukbe.tamlagukbe.auth.jwt;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tamlagukbe.tamlagukbe.auth.jwt.dto.CustomUserDto;

public record JwtUserDetails(CustomUserDto customUserDto) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(customUserDto.getRoleType()));
    }

    @Override
    public String getUsername() {
        return customUserDto.getEmail();
    }

    @Override
    public String getPassword() {
        // 비밀번호가 필요하지 않기때문에 null 반환
        return null;
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

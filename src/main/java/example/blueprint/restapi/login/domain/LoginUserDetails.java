package example.blueprint.restapi.login.domain;

import example.blueprint.restapi.login.application.dto.LoginAuthDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginUserDetails implements UserDetails {

    private LoginAuthDto loginAuthDto;


    public static LoginUserDetails createLoginUserDetail(LoginAuthDto loginAuthDto) {
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        loginUserDetails.loginAuthDto = loginAuthDto;
        return loginUserDetails;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        this.loginAuthDto.getRoles().forEach(role -> authorities.add(role::name));
        return authorities;
    }

    @Override
    public String getPassword() {
        return loginAuthDto.getPassword();
    }

    @Override
    public String getUsername() {
        return loginAuthDto.getUsername();
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
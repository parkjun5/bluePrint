package example.blueprint.restapi.login.application;

import example.blueprint.restapi.login.application.dto.LoginAuthDto;
import example.blueprint.restapi.login.domain.LoginUserDetails;
import example.blueprint.restapi.member.domain.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginDetailsService implements UserDetailsService {

    //TODO 맴버 서비스와 연결
//    private final MemberService memberService;

    @Override
    public LoginUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO 맴버 서비스에서 값 찾아 전달
        LoginAuthDto build = LoginAuthDto.builder().username("testUser").password("비번").roles(Collections.singleton(MemberRole.CUSTOMER)).build();
        return LoginUserDetails.createLoginUserDetail(build);
    }
}

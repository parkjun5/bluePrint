package example.blueprint.restapi.common.security;

import example.blueprint.restapi.common.security.jwt.JwtAuthorizationFilter;
import example.blueprint.restapi.login.application.LoginDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;

    private final LoginDetailsService loginDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(loginDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public JwtAuthorizationFilter getJwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(authenticationManager(), loginDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(auth ->
                        auth.mvcMatchers("/", "/member/signup", "/login", "/swagger-ui.html/**", "/swagger-ui.html#/**", "/v2/api-docs", "/configuration/ui",
                                        "/configuration/security", "/webjars/**", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**").permitAll()
                .mvcMatchers("/**").permitAll())
                .httpBasic().disable()
                .formLogin().disable()
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(getJwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }
}

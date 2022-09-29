package example.blueprint.restapi.common.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import example.blueprint.restapi.login.domain.LoginUserDetails;
import example.blueprint.restapi.login.exception.TokenNotValidException;
import example.blueprint.restapi.login.application.LoginDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final LoginDetailsService loginDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (emptyJwtTokenInHeader(request, response, filterChain)) return;

        String username = verifyAndGetUsername(request);

        if (StringUtils.hasText(username)) {
            setAuthToSecurityContextHolder(username);
        }

        filterChain.doFilter(request, response);
    }

    private boolean emptyJwtTokenInHeader(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    private String verifyAndGetUsername(HttpServletRequest request) {
        String username;
        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        log.debug("access Jwt Token : {}", token);
        try {
            username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                    .getClaim("username").asString();
        } catch (TokenExpiredException tokenExpiredException) {
            throw new TokenNotValidException();
        }
        return username;
    }

    private void setAuthToSecurityContextHolder(String username) {
        LoginUserDetails loginUserDetails = loginDetailsService.loadUserByUsername(username);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserDetails,
                null,
                loginUserDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

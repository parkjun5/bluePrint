package example.blueprint.restapi.common.security.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtProperties {

    @Value("${jwt.secret.key}")
    private static String secretKey;
    public static final String SECRET = secretKey;
    public static final int EXPIRATION_TIME = 300000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}

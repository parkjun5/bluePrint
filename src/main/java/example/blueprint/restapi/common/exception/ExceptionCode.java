package example.blueprint.restapi.common.exception;

import example.blueprint.restapi.login.exception.TokenNotValidException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ERROR_CODE(0000, "해당 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),
    TOKEN_NOT_VALID(1000, "토큰이 더이상 유효하지 않습니다. 재발급을 위해 다시 로그인 해주세요.", TokenNotValidException.class);

    private int code;
    private String message;
    private Class<? extends BadRequestException> type;

    public static ExceptionCode findByClass(Class<?> type) {
        return Arrays.stream(ExceptionCode.values())
                .filter(code -> code.type.equals(type))
                .findAny()
                .orElseThrow(NotFoundErrorCodeException::new);
    }
}

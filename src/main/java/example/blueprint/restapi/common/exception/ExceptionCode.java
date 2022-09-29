package example.blueprint.restapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ERROR_CODE(0000, "해당 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class);

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

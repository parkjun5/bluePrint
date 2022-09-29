package example.blueprint.restapi.common.exception;

import example.blueprint.restapi.login.exception.TokenNotValidException;
import example.blueprint.restapi.member.exception.DuplicateMemberException;
import example.blueprint.restapi.member.exception.MemberSignupException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ERROR_CODE(0, "해당 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),
    TOKEN_NOT_VALID(1000, "토큰이 더이상 유효하지 않습니다. 재발급을 위해 다시 로그인 해주세요.", TokenNotValidException.class),
    MEMBER_SIGNUP_FAIL(1001, "회원가입 중 오류가 발생하였습니다.", MemberSignupException .class),
    MEMBER_DUPLICATE_ERROR(1002, "중복된 회원 정보입니다.", DuplicateMemberException.class)
    ;

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

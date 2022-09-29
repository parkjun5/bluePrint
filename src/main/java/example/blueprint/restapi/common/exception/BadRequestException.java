package example.blueprint.restapi.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private ExceptionCode codeAndMessage = ExceptionCode.findByClass(this.getClass());
    private int code;
    private String message;

    public BadRequestException() {
        this.message = codeAndMessage.getMessage();
        this.code = codeAndMessage.getCode();
    }

}

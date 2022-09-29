package example.blueprint.restapi.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<Object> badRequestExceptionHandler(BadRequestException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> runtimeExceptionHandler(Exception e) {
        if (e.getMessage() == null) {
            log.error(e.getClass().toString());
            log.error(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n")));
        } else {
            log.error(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto(500, "알 수 없는 에러"));
    }

}

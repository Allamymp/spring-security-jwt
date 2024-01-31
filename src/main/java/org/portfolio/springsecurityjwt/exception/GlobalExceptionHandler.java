package org.portfolio.springsecurityjwt.exception;

import org.portfolio.springsecurityjwt.model.record.ErrorResponseRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<?> notFoundEntityException(UserNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseRecord(
                "Entity not found",
                exception.getMessage(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseRecord(
                "Internal Server Error",
                e.getMessage(),
                LocalDateTime.now()
        ));
    }

}

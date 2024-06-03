package com.restapis.expensetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        e.printStackTrace();

        ProblemDetail problemDetail;
        if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
            methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(objectError -> methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                problemDetail.setProperty(fieldName, message);
            }));
        } else if (e instanceof RestException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        } else {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }

        return problemDetail;
    }
}

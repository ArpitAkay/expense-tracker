package com.restapis.expensetracker.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.net.URISyntaxException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String VALIDATION_ERROR = "Validation Error";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) throws URISyntaxException {
        e.printStackTrace();

        ProblemDetail problemDetail;
        if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, VALIDATION_ERROR);
            methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(objectError -> methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                problemDetail.setProperty(fieldName, message);
            }));
        } else if (e instanceof RestException || e instanceof MethodArgumentTypeMismatchException
                || e instanceof BadCredentialsException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        } else if (e instanceof AccessDeniedException
                || e instanceof SignatureException || e instanceof ExpiredJwtException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        } else {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }

        return problemDetail;
    }
}

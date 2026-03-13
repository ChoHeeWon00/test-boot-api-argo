package com.ex01.basic.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthException {
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ProblemDetail> handlerBadCredentials(
                                InvalidPasswordException invalidPasswordException ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("인증 실패");
        problemDetail.setDetail( invalidPasswordException.getMessage() );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( problemDetail );
    }
}

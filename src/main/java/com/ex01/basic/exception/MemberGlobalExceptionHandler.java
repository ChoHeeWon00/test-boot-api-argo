package com.ex01.basic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberGlobalExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ProblemDetail> notFoundHandler(MemberNotFoundException memberNotFoundException){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("사용자 없음");
        problemDetail.setDetail( memberNotFoundException.getMessage() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
    @ExceptionHandler(MemberDuplicateException.class)
    public ResponseEntity<ProblemDetail> duplicateHandler(MemberDuplicateException memberDuplicateException){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("중복 사용자");
        problemDetail.setDetail( memberDuplicateException.getMessage() );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ProblemDetail> invalidHandler(InvalidLoginException invalidLoginException){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("로그인 실패");
        problemDetail.setDetail( invalidLoginException.getMessage() );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }
    @ExceptionHandler(MemberAccessDeniedException.class)
    public ResponseEntity<ProblemDetail> accessDeniedHandler(MemberAccessDeniedException memberAccessDeniedException){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("접근 권한 없음");
        problemDetail.setDetail( memberAccessDeniedException.getMessage() );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }
}

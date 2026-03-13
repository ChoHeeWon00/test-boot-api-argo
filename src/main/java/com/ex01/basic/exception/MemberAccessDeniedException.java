package com.ex01.basic.exception;

public class MemberAccessDeniedException extends RuntimeException{
    public MemberAccessDeniedException(){}
    public MemberAccessDeniedException(String msg){
        super(msg);
    }
}

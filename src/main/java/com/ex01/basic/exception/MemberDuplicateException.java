package com.ex01.basic.exception;

public class MemberDuplicateException extends RuntimeException{
    public MemberDuplicateException(){}
    public MemberDuplicateException(String msg){
        super(msg);
    }
}

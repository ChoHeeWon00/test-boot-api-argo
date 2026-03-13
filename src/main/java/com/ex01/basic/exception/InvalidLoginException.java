package com.ex01.basic.exception;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(){}
    public InvalidLoginException(String msg){
        super(msg);
    }
}

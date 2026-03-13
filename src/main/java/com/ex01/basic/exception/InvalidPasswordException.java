package com.ex01.basic.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidPasswordException extends BadCredentialsException {
    public InvalidPasswordException(String msg){
        super(msg);
    }
}

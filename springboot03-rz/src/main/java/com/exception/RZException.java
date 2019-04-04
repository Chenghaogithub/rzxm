package com.exception;

public class RZException extends RuntimeException {
    public RZException(){}

    public RZException(String msg){
        super(msg);
    }

    public RZException(String msg,Throwable throwable){
        super(msg,throwable);
    }

}

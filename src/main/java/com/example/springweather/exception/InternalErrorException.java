package com.example.springweather.exception;

public class InternalErrorException extends Exception{
    public InternalErrorException() {
        super("Internal error");
    }

    public InternalErrorException(String message) {
        super(message);
    }
}

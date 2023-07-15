package com.example.springweather.exception;


public class IncorrectСityNameException extends Exception{
    public IncorrectСityNameException() {
        this("Incorrect city name");
    }

    public IncorrectСityNameException(String message) {
        super(message);
    }

    public IncorrectСityNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectСityNameException(Throwable cause) {
        super(cause);
    }

    protected IncorrectСityNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

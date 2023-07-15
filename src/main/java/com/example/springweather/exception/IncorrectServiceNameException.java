package com.example.springweather.exception;

public class IncorrectServiceNameException extends Exception{

    public IncorrectServiceNameException() {
        this("Unknown service name");
    }

    public IncorrectServiceNameException(String message) {
        super(message);
    }

    public IncorrectServiceNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectServiceNameException(Throwable cause) {
        super(cause);
    }

    protected IncorrectServiceNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

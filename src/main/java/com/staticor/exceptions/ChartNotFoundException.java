package com.staticor.exceptions;

public class ChartNotFoundException extends Exception {

    public ChartNotFoundException() {
        super("Chart not found to given id!");
    }

    public ChartNotFoundException(String message) {
        super(message);
    }

    public ChartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChartNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ChartNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

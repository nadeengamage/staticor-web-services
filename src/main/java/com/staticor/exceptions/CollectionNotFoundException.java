package com.staticor.exceptions;

public class CollectionNotFoundException extends Exception {

    public CollectionNotFoundException() {
        super("Collection not found to given id!");
    }

    public CollectionNotFoundException(String message) {
        super(message);
    }

    public CollectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CollectionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

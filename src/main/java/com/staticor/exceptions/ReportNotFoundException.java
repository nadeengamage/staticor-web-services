package com.staticor.exceptions;

public class ReportNotFoundException extends Exception {

    public ReportNotFoundException() {
        super("Report not found to given id!");
    }

    public ReportNotFoundException(String message) {
        super(message);
    }

    public ReportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ReportNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.talent.exceptions;

public class CustomAnalysisException extends RuntimeException {
    public CustomAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}
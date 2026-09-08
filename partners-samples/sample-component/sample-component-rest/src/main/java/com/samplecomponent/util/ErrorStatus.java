package com.samplecomponent.util;

import java.io.Serializable;

/**
 * Standard error response for REST endpoints.
 * Only exposes a safe message to the client — never internal exception details.
 */
public class ErrorStatus implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String message;

    public ErrorStatus(String message) {
        this.message = message;
    }

    public ErrorStatus(Throwable exception) {
        this.message = exception.getMessage() != null
                ? exception.getMessage()
                : "An unexpected error occurred.";
    }

    public String getMessage() {
        return message;
    }
}

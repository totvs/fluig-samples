package com.samplecomponent.util;

import java.io.Serializable;

/**
 * Standard error response for REST endpoints.
 * The message must be localized before this response is created.
 */
public class ErrorStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;

    public ErrorStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

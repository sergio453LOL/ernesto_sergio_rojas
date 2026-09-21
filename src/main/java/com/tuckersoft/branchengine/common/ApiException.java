package com.tuckersoft.branchengine.common;

import org.springframework.http.HttpStatus;

/** Base de todas las excepciones de negocio: cada una sabe su codigo HTTP. */
public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String error;

    protected ApiException(HttpStatus status, String error, String message) {
        super(message);
        this.status = status;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}

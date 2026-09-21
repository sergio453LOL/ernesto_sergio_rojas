package com.tuckersoft.branchengine.common;

import java.time.Instant;

/** Formato unico de error del enunciado: { error, message, timestamp, path }. */
public record ErrorResponse(String error, String message, Instant timestamp, String path) {

    public static ErrorResponse de(String error, String message, String path) {
        return new ErrorResponse(error, message, Instant.now(), path);
    }
}

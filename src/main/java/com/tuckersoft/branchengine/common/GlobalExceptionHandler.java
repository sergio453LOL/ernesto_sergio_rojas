package com.tuckersoft.branchengine.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

/**
 * Todos los errores de la API salen por aqui con el formato del enunciado.
 * Los 401 y 403 que dispara Spring Security antes de llegar al controller los
 * escriben RestAuthenticationEntryPoint y RestAccessDeniedHandler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> negocio(ApiException ex, HttpServletRequest req) {
        return responder(ex.getStatus(), ex.getError(), ex.getMessage(), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validacion(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(this::describir)
                .collect(Collectors.joining("; "));
        if (detalle.isBlank()) {
            detalle = "El cuerpo de la peticion no es valido";
        }
        return responder(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", detalle, req);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> peticionInvalida(Exception ex, HttpServletRequest req) {
        return responder(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR",
                "La peticion no es valida: " + ex.getMessage(), req);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accesoDenegado(AccessDeniedException ex, HttpServletRequest req) {
        return responder(HttpStatus.FORBIDDEN, "FORBIDDEN",
                "No tienes permiso para acceder a este recurso", req);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> integridad(DataIntegrityViolationException ex, HttpServletRequest req) {
        return responder(HttpStatus.CONFLICT, "CONFLICT",
                "El recurso entra en conflicto con uno existente", req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> inesperado(Exception ex, HttpServletRequest req) {
        return responder(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                ex.getMessage() == null ? "Error interno" : ex.getMessage(), req);
    }

    private String describir(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }

    private ResponseEntity<ErrorResponse> responder(HttpStatus status, String codigo,
                                                    String mensaje, HttpServletRequest req) {
        return ResponseEntity.status(status)
                .body(ErrorResponse.de(codigo, mensaje, req.getRequestURI()));
    }
}

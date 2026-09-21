package com.tuckersoft.branchengine.decision.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/** Estructura de pagina del enunciado: content, totalElements, totalPages, currentPage, size. */
public record PageResponse<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int size) {

    public static <E, T> PageResponse<T> de(Page<E> pagina, Function<E, T> mapeo) {
        return new PageResponse<>(
                pagina.getContent().stream().map(mapeo).toList(),
                pagina.getTotalElements(),
                pagina.getTotalPages(),
                pagina.getNumber(),
                pagina.getSize());
    }
}

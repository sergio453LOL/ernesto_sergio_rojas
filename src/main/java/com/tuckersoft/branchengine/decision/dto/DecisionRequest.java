package com.tuckersoft.branchengine.decision.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DecisionRequest(

        @NotNull(message = "el playthroughId es obligatorio")
        Long playthroughId,

        @NotBlank(message = "el rawInput es obligatorio")
        @Size(min = 10, message = "el rawInput debe tener al menos 10 caracteres")
        String rawInput,

        @NotBlank(message = "el impactLevel es obligatorio")
        String impactLevel) {
}

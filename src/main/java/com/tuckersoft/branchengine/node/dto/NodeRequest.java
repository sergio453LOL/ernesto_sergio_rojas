package com.tuckersoft.branchengine.node.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NodeRequest(

        @NotBlank(message = "el nodeCode es obligatorio")
        @Size(min = 3, max = 40, message = "el nodeCode debe tener entre 3 y 40 caracteres")
        String nodeCode,

        @NotBlank(message = "el title es obligatorio")
        @Size(min = 3, max = 80, message = "el title debe tener entre 3 y 80 caracteres")
        String title,

        @NotBlank(message = "el sceneText es obligatorio")
        @Size(min = 10, message = "el sceneText debe tener al menos 10 caracteres")
        String sceneText,

        @NotNull(message = "el branchCapacity es obligatorio")
        @Min(value = 1, message = "el branchCapacity debe ser mayor a 0")
        Integer branchCapacity,

        String primaryBranchCode,

        String glitchBranchCode) {
}

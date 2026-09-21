package com.tuckersoft.branchengine.playthrough.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlaythroughRequest(

        @NotBlank(message = "el playerTag es obligatorio")
        @Size(min = 2, max = 40, message = "el playerTag debe tener entre 2 y 40 caracteres")
        String playerTag,

        @NotBlank(message = "el startNodeCode es obligatorio")
        String startNodeCode) {
}

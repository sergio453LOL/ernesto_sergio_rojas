package com.tuckersoft.branchengine.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Si el JSON trae un campo "role" se ignora: el rol se asigna en el service.
 */
public record RegisterRequest(

        @NotBlank(message = "el email es obligatorio")
        @Email(message = "el email no tiene un formato valido")
        String email,

        @NotBlank(message = "la contrasena es obligatoria")
        @Size(min = 6, message = "la contrasena debe tener al menos 6 caracteres")
        String password,

        @NotBlank(message = "el displayName es obligatorio")
        @Size(min = 3, max = 60, message = "el displayName debe tener entre 3 y 60 caracteres")
        String displayName) {
}

package com.aluracursos.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionCorreo(
        @NotBlank @Email String email,
        @NotBlank String nombreUsuario,
        @NotBlank String contrasena
) {
}

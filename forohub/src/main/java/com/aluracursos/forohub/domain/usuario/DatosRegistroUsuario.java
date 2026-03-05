package com.aluracursos.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DatosRegistroUsuario(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank String nombreUsuario,
        @NotBlank @Email String email,
        @NotBlank String contrasena,
        @NotNull Perfil perfil
) {
}

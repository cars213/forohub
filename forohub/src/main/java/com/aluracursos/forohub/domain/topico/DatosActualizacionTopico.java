package com.aluracursos.forohub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
        @NotNull Long id,
        @NotNull Long idCurso,
        @NotNull Long idRespuesta,
        String titulo,
        String mensaje
) {

}

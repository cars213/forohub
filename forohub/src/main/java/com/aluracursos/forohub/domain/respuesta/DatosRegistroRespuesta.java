package com.aluracursos.forohub.domain.respuesta;

import com.aluracursos.forohub.domain.topico.Topico;
import com.aluracursos.forohub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosRegistroRespuesta(
        Long idTopico,
        String mensaje
) {
}

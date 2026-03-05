package com.aluracursos.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String autor,
        String tituloTopico
) {
    public DatosDetalleRespuesta(Respuesta respuesta) {
        this(respuesta.getId(),respuesta.getMensaje(),respuesta.getFechaCreacion(),respuesta.getUsuario().getNombreUsuario(),respuesta.getTopico().getTitulo());
    }
}

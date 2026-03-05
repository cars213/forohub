package com.aluracursos.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        String usuario,
        String curso,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Estado estado
) {
    public DatosDetalleTopico (Topico topico){
        this(topico.getUsuario().getNombreUsuario(),topico.getCurso().getNombre(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),topico.getEstado());
    }
}

package com.aluracursos.forohub.domain.usuario;

public record DatosListaUsuario(
        Long id,
        String nombre,
        String apellido,
        Perfil perfil
) {


    public DatosListaUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getPerfil());
    }
}

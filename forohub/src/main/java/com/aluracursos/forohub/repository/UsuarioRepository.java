package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findByEmail(String email);
    Usuario findByNombreUsuario(String nombreUsuario);

    Page<Usuario> findAllByActivoTrue(Pageable paginacion);
}

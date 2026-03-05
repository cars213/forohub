package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.domain.respuesta.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {
    Page<Respuesta> findByActivaTrue(Pageable paginacion);
}

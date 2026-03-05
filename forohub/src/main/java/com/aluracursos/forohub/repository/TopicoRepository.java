package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.domain.topico.Topico;
import com.aluracursos.forohub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico,Long> {


    @Query("SELECT t FROM Topico t WHERE t.estado IN ('OPEN', 'CLOSED')")
    Page<Topico> buscarTopicosResueltosYNoResueltos(Pageable paginacion);
}

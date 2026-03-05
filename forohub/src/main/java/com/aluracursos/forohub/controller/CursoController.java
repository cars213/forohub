package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.curso.Curso;
import com.aluracursos.forohub.domain.curso.DatosActualizacionCurso;
import com.aluracursos.forohub.domain.curso.DatosListaCurso;
import com.aluracursos.forohub.domain.curso.DatosRegistroCurso;
import com.aluracursos.forohub.domain.usuario.*;
import com.aluracursos.forohub.infra.exceptions.AccesoDenegadoException;
import com.aluracursos.forohub.repository.CursoRepository;
import com.aluracursos.forohub.repository.UsuarioRepository;
import com.aluracursos.forohub.service.RecuperarIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {


    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RecuperarIdService recuperarIdService;

    @Transactional
    @PostMapping
    @Operation(summary = "Creacion de cursos, solo profesores y administradores pueden crear cursos")
    public ResponseEntity registrarCurso (@RequestBody @Valid DatosRegistroCurso datosRegistroCurso, HttpServletRequest request){
        var curso = new Curso(datosRegistroCurso);
        var usuario = usuarioRepository.getReferenceById(recuperarIdService.obtenerId(request));
        if(usuario.getPerfil() == Perfil.ADMINISTRADOR || usuario.getPerfil() == Perfil.PROFESOR){
            cursoRepository.save(curso);
            return ResponseEntity.ok().build();
        }else{
            throw new AccesoDenegadoException("No tienes el rango para crear un curso");
        }

    }

    @GetMapping("/all")
    @Operation(summary = "Lista de todos los cursos disponibles en el foro")
    public ResponseEntity<Page<DatosListaCurso>> listarCursos (@PageableDefault(size = 5, sort = {"nombre"}) Pageable paginacion){
        var page = cursoRepository.findAllByActivoTrue(paginacion)
                .map(DatosListaCurso::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busqueda de curso por id")
    public ResponseEntity detallarCurso (@PathVariable Long id){
        var curso = cursoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosListaCurso(curso));
    }

    @Transactional
    @PutMapping
    @Operation(summary = "Actualizacion de curso, solo profesores y administradores pueden actualizar cursos")
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionCurso datosActualizacionCurso, HttpServletRequest request){
        var usuario = usuarioRepository.getReferenceById(recuperarIdService.obtenerId(request));
        var curso = cursoRepository.getReferenceById(datosActualizacionCurso.id());
        System.out.println(usuario.getPerfil() == Perfil.ADMINISTRADOR || usuario.getPerfil() == Perfil.PROFESOR);

        if(usuario.getPerfil() == Perfil.ADMINISTRADOR || usuario.getPerfil() == Perfil.PROFESOR){
            curso.actualizarCurso(datosActualizacionCurso);
            return ResponseEntity.ok(new DatosListaCurso(curso));
        }else {
            throw new AccesoDenegadoException("No tienes el rango para actualizar un curso");
        }


    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminacion de curso, solo profesores y administradores pueden eliminar cursos")
    public ResponseEntity eliminar(@PathVariable Long id, HttpServletRequest request){
        var usuario = usuarioRepository.getReferenceById(recuperarIdService.obtenerId(request));
        var curso = cursoRepository.getReferenceById(id);
        if(usuario.getPerfil() == Perfil.ADMINISTRADOR || usuario.getPerfil() == Perfil.PROFESOR){
            curso.eliminar();
            return ResponseEntity.noContent().build();
        }else{
            throw new AccesoDenegadoException("No tienes el rango para eliminar un curso");
        }


    }


}

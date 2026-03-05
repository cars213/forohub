package com.aluracursos.forohub.controller;


import com.aluracursos.forohub.domain.topico.DatosActualizacionTopico;
import com.aluracursos.forohub.domain.topico.DatosDetalleTopico;
import com.aluracursos.forohub.domain.topico.DatosRegistroTopico;
import com.aluracursos.forohub.domain.topico.Topico;
import com.aluracursos.forohub.infra.exceptions.AccesoDenegadoException;
import com.aluracursos.forohub.repository.CursoRepository;
import com.aluracursos.forohub.repository.RespuestaRepository;
import com.aluracursos.forohub.repository.TopicoRepository;
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
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    RecuperarIdService recuperarIdService;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    RespuestaRepository respuestaRepository;


    //Create
    @Transactional
    @PostMapping
    @Operation(summary = "Registrar topico, el topico se acopla automaticamente al usuario")
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos,HttpServletRequest request){
        var usuario = usuarioRepository.getReferenceById(recuperarIdService.obtenerId(request));
        var curso = cursoRepository.getReferenceById(datos.idCurso());
        var topico = new Topico(datos,usuario,curso);
        topicoRepository.save(topico);
        System.out.println(topico);
        return ResponseEntity.ok().build();
    }

    //Leer
    @GetMapping("/all")
    @Operation(summary = "Lista de todos los topicos resueltos y no resueltos")
    public ResponseEntity<Page<DatosDetalleTopico>> listar(@PageableDefault(size = 5, sort = {"titulo"}) Pageable paginacion){
        var page = topicoRepository.buscarTopicosResueltosYNoResueltos(paginacion)
                .map(DatosDetalleTopico::new);

        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Busqueda en detalle de topico por id")
    @GetMapping("/{id}")
    public ResponseEntity detallar (@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));

    }

    //Actualizar
    @Transactional
    @PutMapping
    @Operation(summary = "Actualizacion de topico, solo puede actualizar los topicos donde la id asignada al token sea la misma que la del usuario autor del topico")
    public ResponseEntity actualizar(@RequestBody DatosActualizacionTopico datos, HttpServletRequest request){
        var userId = recuperarIdService.obtenerId(request);
        var topico = topicoRepository.getReferenceById(datos.id());
        var respuesta = respuestaRepository.getReferenceById(datos.idRespuesta());
        var curso = cursoRepository.getReferenceById(datos.idCurso());

        if(topico.getUsuario().getId() == userId){
            topico.actualizarTopico(datos,respuesta,curso);
            return ResponseEntity.ok(new DatosDetalleTopico(topico));
        }else {
            throw new AccesoDenegadoException("No se pudo actualizar el topico, no eres el autor del topico seleccionada");
        }

    }

    //Eliminar
    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminacion de topico, solo puede eliminar los topicos donde la id asignada al token sea la misma que la del usuario autor del topico")
    public ResponseEntity eliminar(@PathVariable Long id,HttpServletRequest request){
        var topico = topicoRepository.getReferenceById(id);
        var userId = recuperarIdService.obtenerId(request);
        if(topico.getUsuario().getId() == userId){
            topico.eliminar();
            return ResponseEntity.noContent().build();
        }else {
            throw new AccesoDenegadoException("No se pudo eliminar el topico, no eres el autor del topico seleccionada");
        }

    }




}

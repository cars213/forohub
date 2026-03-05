package com.aluracursos.forohub.controller;


import com.aluracursos.forohub.domain.respuesta.DatosActualizarRespuesta;
import com.aluracursos.forohub.domain.respuesta.DatosDetalleRespuesta;
import com.aluracursos.forohub.domain.respuesta.DatosRegistroRespuesta;
import com.aluracursos.forohub.domain.respuesta.Respuesta;
import com.aluracursos.forohub.domain.topico.DatosActualizacionTopico;
import com.aluracursos.forohub.domain.topico.DatosDetalleTopico;
import com.aluracursos.forohub.domain.usuario.DatosRegistroUsuario;
import com.aluracursos.forohub.domain.usuario.Usuario;
import com.aluracursos.forohub.infra.exceptions.AccesoDenegadoException;
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
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    RecuperarIdService recuperarIdService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    RespuestaRepository respuestaRepository;


    @Transactional
    @PostMapping
    @Operation(summary = "Registrar Respuesta, la respuesta se asocia automaticamente al usuario con sesion iniciada")
    public ResponseEntity registrarRespuesta (@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta, HttpServletRequest request){
        var usuario = usuarioRepository.getReferenceById(recuperarIdService.obtenerId(request));
        var topico = topicoRepository.getReferenceById(datosRegistroRespuesta.idTopico());
        var respuesta = new Respuesta(datosRegistroRespuesta,usuario,topico);
        respuestaRepository.save(respuesta);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Lista de las respuestas activas")
    public ResponseEntity<Page<DatosDetalleRespuesta>> listar(@PageableDefault(size = 5, sort = {"id"}) Pageable paginacion){
        var page = respuestaRepository.findByActivaTrue(paginacion)
                .map(DatosDetalleRespuesta::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detallar respuesta por id")
    public ResponseEntity detallar (@PathVariable Long id){
        var respuesta = respuestaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));

    }

    @Transactional
    @PutMapping
    @Operation(summary = "Actualizacion de respuesta, solo puede actualizar las respuestas donde la id asignada al token sea la misma que la del usuario autor del topico")
    public ResponseEntity actualizar(@RequestBody DatosActualizarRespuesta datos, HttpServletRequest request){
        var userId = recuperarIdService.obtenerId(request);
        var respuesta = respuestaRepository.getReferenceById(datos.id());
        if(respuesta.getUsuario().getId() == userId){
            respuesta.setMensaje(datos.mensaje());
            return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
        }else {
            throw new AccesoDenegadoException("No se pudo actualizar la respuesta, no eres el autor de la respuesta seleccionada");
        }

    }

    //Eliminar
    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminacion de respuesta, solo puede eliminar las respuestas donde la id asignada al token sea la misma que la del usuario autor del topico")
    public ResponseEntity eliminar(@PathVariable Long id,HttpServletRequest request){
        var respuesta = respuestaRepository.getReferenceById(id);
        var userId = recuperarIdService.obtenerId(request);
        if(respuesta.getUsuario().getId() == userId){
            respuesta.eliminar();
            return ResponseEntity.noContent().build();
        }else {
            throw new AccesoDenegadoException("No se pudo eliminar la respuesta, no eres el autor de la respuesta seleccionada");
        }

    }



}

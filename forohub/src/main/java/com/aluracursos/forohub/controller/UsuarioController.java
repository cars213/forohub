package com.aluracursos.forohub.controller;


import com.aluracursos.forohub.domain.usuario.*;
import com.aluracursos.forohub.domain.usuario.Usuario;
import com.aluracursos.forohub.infra.exceptions.AccesoDenegadoException;
import com.aluracursos.forohub.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping
    @Operation(summary = "Registro de usuario")
    public ResponseEntity registrarUsuario (@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario){
        var hashContrasena = passwordEncoder.encode(datosRegistroUsuario.contrasena());
        var usuario = new Usuario(datosRegistroUsuario,hashContrasena);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Lista de todos los usuarios activos")
    public ResponseEntity<Page<DatosListaUsuario>> listar (@PageableDefault(size = 5, sort = {"nombre"}) Pageable paginacion){
        var page = usuarioRepository.findAllByActivoTrue(paginacion)
                .map(DatosListaUsuario::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping("/contrasena")
    @Operation(summary = "Actualizacion de contraseña, se requiere correo y nombre de usuario")
    public ResponseEntity actualizarContrasena(@RequestBody @Valid DatosActualizacionContrasena datosActualizacionContrasena){
        var hashContrasena = passwordEncoder.encode(datosActualizacionContrasena.contrasena());
        var usuario = usuarioRepository.findByEmail(datosActualizacionContrasena.email());
        if(datosActualizacionContrasena.nombreUsuario().equalsIgnoreCase(usuario.getNombreUsuario())){
            usuario.ActualizarContrasena(hashContrasena);
            return ResponseEntity.ok().build();
        }else {
            throw new AccesoDenegadoException("No se pudo actualizar la contraseña, nombre de usuario o correo incorrectos");
        }
    }

    @Transactional
    @PutMapping("/correo")
    @Operation(summary = "Atualizacion de correo, se requiere nombre de usuario y contraseña")
    public ResponseEntity actualizarCorreo(@RequestBody @Valid DatosActualizacionCorreo datosActualizacionCorreo){
        var usuario = usuarioRepository.findByNombreUsuario(datosActualizacionCorreo.nombreUsuario());
        if(passwordEncoder.matches(datosActualizacionCorreo.contrasena(), usuario.getPassword())){
            usuario.ActualizarCorreo(datosActualizacionCorreo);
            return ResponseEntity.ok().build();
        }else {
            throw new AccesoDenegadoException("No se pudo actualizar el correo, nombre de usuario o contraseña incorrectos");
        }

    }

    @Transactional
    @PutMapping()
    @Operation(summary = "Actualizacion de usuario, se requiere correo y contraseña")
    public ResponseEntity actualizarUsuario(@RequestBody @Valid DatosActualizacionUsuario datosActualizacionUsuario){
        var usuario = usuarioRepository.findByEmail(datosActualizacionUsuario.email());
        if(passwordEncoder.matches(datosActualizacionUsuario.contrasena(), usuario.getPassword())){
            usuario.ActualizarUsuario(datosActualizacionUsuario);
            return ResponseEntity.ok().build();
        }else {
            throw new AccesoDenegadoException("No se pudo actualizar el usuario, correo o contraseña incorrectos");
        }

    }

    @Transactional
    @DeleteMapping
    @Operation(summary = "Eliminacion de usuario, se requiere correo y contraseña del usuario a eliminar")
    public ResponseEntity eliminarUsuario(@RequestBody @Valid DatosActualizacionContrasena datosActualizacionContrasena){
        var usuario = usuarioRepository.findByEmail(datosActualizacionContrasena.email());
        if(passwordEncoder.matches(datosActualizacionContrasena.contrasena(), usuario.getPassword())){
            usuario.EliminarUsuario();
            return ResponseEntity.ok().build();
        }else {
            throw new AccesoDenegadoException("No se pudo eliminar el usuario, correo o contraseña incorrectos");
        }

    }

}

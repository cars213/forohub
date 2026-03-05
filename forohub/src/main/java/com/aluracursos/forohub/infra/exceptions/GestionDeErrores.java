package com.aluracursos.forohub.infra.exceptions;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestionDeErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionarError400(MethodArgumentNotValidException exception){
        var errores = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList());
    }

    @ExceptionHandler(AccesoDenegadoException.class)
    public ResponseEntity gestionarError403(AccesoDenegadoException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DatosErrorAcceso(exception));
    }


    public record DatosErrorAcceso(String mensaje){
        public DatosErrorAcceso(AccesoDenegadoException exception){
            this(exception.getMessage());
        }
    }

    public record DatosErrorValidacion(String campo, String mensaje){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }



}

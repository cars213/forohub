package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.curso.Curso;
import com.aluracursos.forohub.domain.respuesta.Respuesta;
import com.aluracursos.forohub.domain.usuario.Usuario;
import com.aluracursos.forohub.repository.CursoRepository;
import com.aluracursos.forohub.repository.UsuarioRepository;
import com.aluracursos.forohub.service.TokenService;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;


    public Topico(DatosRegistroTopico datosRegistroTopico,Usuario usuario,Curso curso) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.estado = Estado.OPEN;
        this.curso = curso;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "titulo = " + titulo +
                ", autor = [ " + usuario +
                "] , mensaje = " + mensaje +
                ", fechaCreacion = " + fechaCreacion +
                ", estado = " + estado +
                ", curso = [ " + curso + " ] ";
    }

    public void actualizarTopico(@Valid DatosActualizacionTopico datos, Respuesta respuesta, Curso curso) {
        if(curso != null){
            this.curso = curso;
        }
        if(respuesta != null){
            respuesta.setSolucion(true);
            this.estado = Estado.CLOSED;
        }
        if(datos.titulo() != null){
            this.titulo = datos.titulo();
        }
        if(datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }
    }

    public void eliminar(){
        this.estado = Estado.DELETED;
    }



}

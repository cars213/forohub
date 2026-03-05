package com.aluracursos.forohub.domain.respuesta;

import com.aluracursos.forohub.domain.topico.Topico;
import com.aluracursos.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String mensaje;
    private boolean activa;
    @Setter
    private boolean solucion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Usuario usuario, Topico topico) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.solucion = false;
        this.usuario = usuario;
        this.topico = topico;
        this.activa = true;
    }

    public void eliminar(){
        this.activa = false;
    }


    @Override
    public String toString() {
        return "Respuesta [ " +
                "id = " + id +
                ", mensaje = " + mensaje +
                ", fechaCreacion = " + fechaCreacion +
                ", usuario = [ " + usuario +
                ", ] topico = [ " + topico +
                "] ]";
    }

}

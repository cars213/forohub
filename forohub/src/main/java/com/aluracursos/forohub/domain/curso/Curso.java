package com.aluracursos.forohub.domain.curso;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="cursos")
@Entity(name="Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private boolean activo;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Curso(DatosRegistroCurso datosRegistroCurso) {
        this.nombre = datosRegistroCurso.nombre();
        this.categoria = datosRegistroCurso.categoria();
        this.activo = true;
    }

    public void actualizarCurso(DatosActualizacionCurso datosActualizacionCurso){
        this.nombre = datosActualizacionCurso.nombre();
        this.categoria = datosActualizacionCurso.categoria();
    }

    public void eliminar(){
        this.activo = false;
    }

    @Override
    public String toString() {
        return "nombre = " + nombre +
                ", categoria = " + categoria;
    }
}

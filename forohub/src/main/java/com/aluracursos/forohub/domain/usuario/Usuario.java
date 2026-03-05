package com.aluracursos.forohub.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Table(name="usuarios")
@Entity(name="Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private boolean activo;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;


    public Usuario(DatosRegistroUsuario datosRegistroUsuario, String hashContrasena) {
        this.nombre = datosRegistroUsuario.nombre();
        this.email = datosRegistroUsuario.email();
        this.nombreUsuario = datosRegistroUsuario.nombreUsuario();
        this.apellido = datosRegistroUsuario.apellido();
        this.contrasena = hashContrasena;
        this.activo = true;
        this.perfil = datosRegistroUsuario.perfil();
    }

    public void ActualizarContrasena(String hashContrasena){
        if (hashContrasena != null){
            this.contrasena = hashContrasena;
        }
    }

    public void ActualizarCorreo(DatosActualizacionCorreo datosActualizacionCorreo){
        if (datosActualizacionCorreo.email() != null){
            this.email = datosActualizacionCorreo.email();
        }
    }

    public void ActualizarUsuario(DatosActualizacionUsuario datosActualizacionUsuario){
        if (datosActualizacionUsuario.nombre() != null){
            this.nombre = datosActualizacionUsuario.nombre();
        }
        if (datosActualizacionUsuario.apellido() != null){
            this.apellido = datosActualizacionUsuario.apellido();
        }
        if (datosActualizacionUsuario.nombreUsuario() != null){
            this.nombreUsuario = datosActualizacionUsuario.nombreUsuario();
        }
    }

    public void EliminarUsuario (){
        this.activo = false;
    }



    @Override
    public String toString() {
        return"nombre = " + nombre + " " + apellido +
                ", Username = " + nombreUsuario +
                ", perfil = " + perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

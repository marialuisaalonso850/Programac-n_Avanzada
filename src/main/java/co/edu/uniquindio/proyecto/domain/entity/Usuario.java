package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@ToString(exclude = {"password", "solicitudes", "historial"})
@NoArgsConstructor(force = true)
public class Usuario {

    private final DocumentoIdentidad identificacion;
    private String nombre;
    private Email email;
    private final TipoUsuario tipoUsuario;
    private String password;
    private EstadoUsuario estado;

    // Constructor para REGISTRO (Utilizado en CrearUsuarioUseCase)
    public Usuario(DocumentoIdentidad identificacion, String nombre, Email email,
                   TipoUsuario tipoUsuario, String password) {

        validarCampos(identificacion, nombre, email, tipoUsuario, password);

        this.identificacion = identificacion;
        this.nombre = nombre;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.password = password;
        this.estado = EstadoUsuario.ACTIVO; // Por defecto nace activo
    }

    private void validarCampos(DocumentoIdentidad id, String nom, Email em, TipoUsuario tipo, String pass) {
        if (id == null) throw new ReglaDominioException("La identificación es obligatoria");
        if (nom == null || nom.isBlank()) throw new ReglaDominioException("El nombre es obligatorio");
        if (em == null) throw new ReglaDominioException("El email es obligatorio");
        if (tipo == null) throw new ReglaDominioException("El tipo de usuario es obligatorio");
        if (pass == null || pass.isBlank()) throw new ReglaDominioException("El password es obligatorio");
    }

    // Métodos de comportamiento
    public void desactivar() {
        if (this.estado == EstadoUsuario.INACTIVO) throw new ReglaDominioException("El usuario ya está inactivo");
        this.estado = EstadoUsuario.INACTIVO;
    }

    public void cambiarEmail(Email nuevoEmail) {
        if (!estaActivo())
            throw new ReglaDominioException("No se puede modificar un usuario inactivo");
        if (nuevoEmail == null)
            throw new ReglaDominioException("El nuevo email es obligatorio");
        this.email = nuevoEmail;
    }

    public boolean estaActivo() {
        return estado == EstadoUsuario.ACTIVO;
    }

    public boolean puedeSerResponsable() {
        return estaActivo() && (esDocente() || esCoordinador());
    }


    public boolean esDocente() { return this.tipoUsuario == TipoUsuario.DOCENTE; }
    public boolean esEstudiante() { return this.tipoUsuario == TipoUsuario.ESTUDIANTE; }

    public void cambiarNombre(String nuevoNombre) {
        if (!estaActivo())
            throw new ReglaDominioException("No se puede modificar un usuario inactivo");
        if (nuevoNombre == null || nuevoNombre.isBlank())
            throw new ReglaDominioException("El nombre no puede estar vacío");
        this.nombre = nuevoNombre;
    }

    public boolean esAdmin() { return this.tipoUsuario == TipoUsuario.ADMIN; }
    public boolean esCoordinador() { return this.tipoUsuario == TipoUsuario.COORDINADOR; }
}
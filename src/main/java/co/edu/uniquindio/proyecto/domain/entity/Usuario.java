package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoUsuario;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Usuario {

    @Getter
    private final DocumentoIdentidad identificacion;
    @Getter
    private String nombre;
    @Getter
    private Email email;
    @Getter
    private final TipoUsuario tipoUsuario;
    @Getter
    private EstadoUsuario estado;

    public Usuario(DocumentoIdentidad identificacion,
                   String nombre,
                   Email email,
                   TipoUsuario tipoUsuario) {

        if (identificacion == null )
            throw new ReglaDominioException("La identificacion es obligatoria");

        if (nombre == null || nombre.isBlank())
            throw new ReglaDominioException("El nombre es obligatorio");

        if (email == null)
            throw new ReglaDominioException("El email es obligatorio");

        if (tipoUsuario == null)
            throw new ReglaDominioException("El tipo de usuario es obligatorio");

        this.identificacion = identificacion;
        this.nombre = nombre;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.estado = EstadoUsuario.ACTIVO;
    }

    public static Usuario crear(DocumentoIdentidad identificacion, String nombre, Email email, TipoUsuario tipoUsuario) {
        // Al crear uno nuevo, siempre nace en ACTIVO por regla de negocio
        return new Usuario(identificacion, nombre, email, tipoUsuario);
    }
    public void desactivar() {

        if (estado == EstadoUsuario.INACTIVO)
            throw new ReglaDominioException("El usuario ya está inactivo");

        this.estado = EstadoUsuario.INACTIVO;
    }

    public void activar() {

        if (estado == EstadoUsuario.ACTIVO)
            throw new ReglaDominioException("El usuario ya está activo");

        this.estado = EstadoUsuario.ACTIVO;
    }

    public boolean estaActivo() {
        return estado == EstadoUsuario.ACTIVO;
    }

    public void cambiarEmail(Email nuevoEmail) {

        if (!estaActivo())
            throw new ReglaDominioException("No se puede modificar un usuario inactivo");

        if (nuevoEmail == null)
            throw new ReglaDominioException("El nuevo email es obligatorio");

        this.email = nuevoEmail;
    }

    public void cambiarNombre(String nuevoNombre) {

        if (!estaActivo())
            throw new ReglaDominioException("No se puede modificar un usuario inactivo");

        if (nuevoNombre == null || nuevoNombre.isBlank())
            throw new ReglaDominioException("El nombre no puede estar vacío");

        this.nombre = nuevoNombre;
    }

    public boolean puedeSerResponsable() {
        return estaActivo() &&
                (esDocente() || esCoordinador());
    }

    public boolean esAdmin() {
        return this.tipoUsuario == TipoUsuario.ADMIN;
    }

    public boolean esCoordinador() {
        return this.tipoUsuario == TipoUsuario.COORDINADOR;
    }

    public boolean esDocente() {
        return this.tipoUsuario == TipoUsuario.DOCENTE;
    }

    public boolean esEstudiante() {
        return this.tipoUsuario == TipoUsuario.ESTUDIANTE;
    }

    public boolean puedeCambiarPrioridad() {
        return estaActivo() &&
                (esAdmin() || esCoordinador());
    }
}
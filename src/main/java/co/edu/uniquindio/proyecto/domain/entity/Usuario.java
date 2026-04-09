package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoUsuario;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;
import lombok.Getter;
import lombok.ToString;

/**
 * Entidad que representa un usuario dentro del sistema.
 *
 * <p>Un usuario posee una identificación única, nombre, correo electrónico,
 * tipo de usuario y estado. Dependiendo de su tipo y estado puede realizar
 * diferentes acciones dentro del sistema como gestionar solicitudes o
 * actuar como responsable.</p>
 */


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

    /**
     * Constructor para crear un nuevo usuario del sistema.
     * El usuario se crea inicialmente con estado ACTIVO.
     *
     * @param identificacion documento de identidad del usuario
     * @param nombre nombre completo del usuario
     * @param email correo electrónico del usuario
     * @param tipoUsuario tipo de usuario dentro del sistema
     */
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

    /**
     * Desactiva el usuario del sistema.
     */

    public static Usuario crear(DocumentoIdentidad identificacion, String nombre, Email email, TipoUsuario tipoUsuario) {
        // Al crear uno nuevo, siempre nace en ACTIVO por regla de negocio
        return new Usuario(identificacion, nombre, email, tipoUsuario);
    }

    public void desactivar() {

        if (estado == EstadoUsuario.INACTIVO)
            throw new ReglaDominioException("El usuario ya está inactivo");

        this.estado = EstadoUsuario.INACTIVO;
    }

    /**
     * Activa nuevamente un usuario que estaba inactivo.
     */
    public void activar() {

        if (estado == EstadoUsuario.ACTIVO)
            throw new ReglaDominioException("El usuario ya está activo");

        this.estado = EstadoUsuario.ACTIVO;
    }

    /**
     * Verifica si el usuario se encuentra activo.
     *
     * @return true si el usuario está activo, false en caso contrario
     */
    public boolean estaActivo() {
        return estado == EstadoUsuario.ACTIVO;
    }

    /**
     * Permite cambiar el correo electrónico del usuario.
     *
     * @param nuevoEmail nuevo correo electrónico
     */
    public void cambiarEmail(Email nuevoEmail) {

        if (!estaActivo())
            throw new ReglaDominioException("No se puede modificar un usuario inactivo");

        if (nuevoEmail == null)
            throw new ReglaDominioException("El nuevo email es obligatorio");

        this.email = nuevoEmail;
    }

    /**
     * Permite modificar el nombre del usuario.
     *
     * @param nuevoNombre nuevo nombre del usuario
     */
    public void cambiarNombre(String nuevoNombre) {

        if (!estaActivo())
            throw new ReglaDominioException("No se puede modificar un usuario inactivo");

        if (nuevoNombre == null || nuevoNombre.isBlank())
            throw new ReglaDominioException("El nombre no puede estar vacío");

        this.nombre = nuevoNombre;
    }

    /**
     * Determina si el usuario puede ser responsable de una solicitud.
     *
     * @return true si el usuario es docente o coordinador y está activo
     */
    public boolean puedeSerResponsable() {
        return estaActivo() &&
                (esDocente() || esCoordinador());
    }

    /**
     * Verifica si el usuario tiene rol de administrador.
     *
     * @return true si es administrador
     */
    public boolean esAdmin() {
        return this.tipoUsuario == TipoUsuario.ADMIN;
    }

    /**
     * Verifica si el usuario tiene rol de coordinador.
     *
     * @return true si es coordinador
     */
    public boolean esCoordinador() {
        return this.tipoUsuario == TipoUsuario.COORDINADOR;
    }

    /**
     * Verifica si el usuario tiene rol de docente.
     *
     * @return true si es docente
     */
    public boolean esDocente() {
        return this.tipoUsuario == TipoUsuario.DOCENTE;
    }

    /**
     * Verifica si el usuario tiene rol de estudiante.
     *
     * @return true si es estudiante
     */
    public boolean esEstudiante() {
        return this.tipoUsuario == TipoUsuario.ESTUDIANTE;
    }

    /**
     * Determina si el usuario tiene permisos para cambiar la prioridad
     * de una solicitud.
     *
     * @return true si el usuario es administrador o coordinador y está activo
     */
    public boolean puedeCambiarPrioridad() {
        return estaActivo() &&
                (esAdmin() || esCoordinador());
    }
}
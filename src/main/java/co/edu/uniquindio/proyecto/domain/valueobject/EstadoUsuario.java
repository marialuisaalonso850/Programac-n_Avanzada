package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa el estado de un usuario dentro del sistema.
 *
 * <p>Define si un usuario está activo y puede interactuar con el sistema,
 * o si está inactivo y no puede realizar acciones.</p>
 *
 * <ul>
 *     <li>ACTIVO: Usuario habilitado para interactuar con el sistema.</li>
 *     <li>INACTIVO: Usuario deshabilitado, no puede acceder ni ejecutar operaciones.</li>
 * </ul>
 */
public enum EstadoUsuario {
    ACTIVO,
    INACTIVO
}
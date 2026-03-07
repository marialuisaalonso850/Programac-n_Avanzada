package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa los tipos de usuario permitidos en el sistema.
 *
 * <p>Define el perfil o rol funcional que determina
 * las responsabilidades y permisos dentro del dominio.</p>
 *
 * <ul>
 *     <li>ADMIN: Usuario con privilegios administrativos.</li>
 *     <li>ESTUDIANTE: Usuario que realiza solicitudes académicas.</li>
 *     <li>COORDINADOR: Responsable de la gestión y supervisión.</li>
 *     <li>DOCENTE: Usuario encargado de actividades académicas.</li>
 * </ul>
 */
public enum TipoUsuario {

    ADMIN,
    ESTUDIANTE,
    COORDINADOR,
    DOCENTE
}
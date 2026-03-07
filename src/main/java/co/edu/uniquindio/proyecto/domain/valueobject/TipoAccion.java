package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa los tipos de acciones que pueden registrarse
 * en el historial de una solicitud dentro del sistema.
 *
 * <p>Se utiliza para auditar los cambios y eventos relevantes
 * ocurridos durante el ciclo de vida de la solicitud.</p>
 *
 * <ul>
 *     <li>SOLICITUD_REGISTRADA: Creación de la solicitud.</li>
 *     <li>CLASIFICADA: La solicitud fue categorizada.</li>
 *     <li>RESPONSABLE_ASIGNADO: Se asignó un responsable.</li>
 *     <li>ATENCION_INICIADA: Se inició el proceso de atención.</li>
 *     <li>PRIORIDAD_MODIFICADA: Cambio en el nivel de prioridad.</li>
 *     <li>SOLICITUD_ATENDIDA: La solicitud fue resuelta.</li>
 *     <li>SOLICITUD_CANCELADA: La solicitud fue cancelada.</li>
 *     <li>SOLICITUD_CERRADA: Se cerró formalmente el caso.</li>
 *     <li>ESTADO_MODIFICADO: Cambio general de estado.</li>
 * </ul>
 */
public enum TipoAccion {

    SOLICITUD_REGISTRADA,
    CLASIFICADA,
    RESPONSABLE_ASIGNADO,
    ATENCION_INICIADA,
    PRIORIDAD_MODIFICADA,
    SOLICITUD_ATENDIDA,
    SOLICITUD_CANCELADA,
    SOLICITUD_CERRADA,
    ESTADO_MODIFICADO
}
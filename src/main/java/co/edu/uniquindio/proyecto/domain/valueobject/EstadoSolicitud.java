package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa el estado de una solicitud dentro del sistema.
 *
 * <p>Define el ciclo de vida de la solicitud desde su creación
 * hasta su cierre o cancelación.</p>
 *
 * <ul>
 *     <li>REGISTRADA: Solicitud creada en el sistema.</li>
 *     <li>CLASIFICADA: Solicitud categorizada según criterios internos.</li>
 *     <li>EN_ATENCION: Está siendo gestionada por un responsable.</li>
 *     <li>ATENDIDA: La solicitud fue resuelta.</li>
 *     <li>CERRADA: Proceso finalizado formalmente.</li>
 *     <li>CANCELADA: Fue anulada antes de su finalización.</li>
 * </ul>
 */
public enum EstadoSolicitud {

    REGISTRADA,
    CLASIFICADA,
    EN_ATENCION,
    ATENDIDA,
    CERRADA,
    CANCELADA
}
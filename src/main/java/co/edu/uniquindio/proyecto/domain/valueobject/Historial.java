package co.edu.uniquindio.proyecto.domain.valueobject;

import java.time.LocalDateTime;

/**
 * Representa un registro de historial de acciones realizadas sobre una entidad
 * dentro del sistema.
 *
 * <p>Contiene la fecha y hora de la acción, el tipo de acción realizada,
 * el usuario responsable y una observación opcional.</p>
 *
 * <ul>
 *     <li>{@code fechaHora}: Fecha y hora de la acción (no puede ser nula).</li>
 *     <li>{@code accion}: Tipo de acción realizada (no puede ser nulo).</li>
 *     <li>{@code usuarioId}: Documento de identidad del usuario responsable (no puede ser nulo).</li>
 *     <li>{@code observacion}: Comentarios u observaciones sobre la acción (opcional).</li>
 * </ul>
 *
 * <p>Es inmutable, ya que está implementado como un {@code record}.</p>
 *
 * @param fechaHora Fecha y hora de la acción
 * @param accion Tipo de acción realizada
 * @param usuarioId Documento de identidad del usuario responsable
 * @param observacion Observación opcional de la acción
 * @throws IllegalArgumentException si {@code fechaHora}, {@code accion} o {@code usuarioId} son nulos
 */
public record Historial(
        LocalDateTime fechaHora,
        TipoAccion accion,
        DocumentoIdentidad usuarioId,
        String observacion
) {

    public Historial {

        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora no puede ser null");
        }

        if (accion == null) {
            throw new IllegalArgumentException("La acción es obligatoria");
        }

        if (usuarioId == null ) {
            throw new IllegalArgumentException("El usuario responsable es obligatorio");
        }

        if (observacion != null) {
            observacion = observacion.trim();
        }
    }
}
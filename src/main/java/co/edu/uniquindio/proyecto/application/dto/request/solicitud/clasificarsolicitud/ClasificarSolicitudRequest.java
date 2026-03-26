package co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import jakarta.validation.constraints.*;
/**
 * DTO para clasificar una solicitud existente
 * Mapea a: Solicitud.clasificar()
 */
public record ClasificarSolicitudRequest(
        @NotNull(message = "El tipo de solicitud es obligatorio")
        Long tipoSolicitudId,
        @NotNull(message = "La prioridad es obligatoria")
        PrioridadDTO prioridad,
        @NotBlank(message = "La justificación es obligatoria")
        @Size(min = 10, max = 500)
        String justificacion
) {}


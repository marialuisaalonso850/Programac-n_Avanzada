package co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad; // Importa tu Enum
import jakarta.validation.constraints.*;

/**
 * DTO para clasificar una solicitud existente.
 * Mapea a: Solicitud.clasificar()
 */
public record ClasificarSolicitudRequest(

        @NotNull(message = "El ID del tipo de solicitud es obligatorio")
        Long tipoSolicitudId,

        @NotNull(message = "La prioridad es obligatoria")
        Prioridad prioridad,

        @NotBlank(message = "La justificación es obligatoria")
        @Size(min = 10, max = 500, message = "La justificación debe tener entre 10 y 500 caracteres")
        String justificacion
) {}

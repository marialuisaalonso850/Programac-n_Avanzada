package co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; /**
 * DTO anidado para Prioridad (Value Object)
 */
public record PrioridadDTO(
        @NotNull
        Prioridad nivel, // CRITICA, ALTA, MEDIA, BAJA
        @NotBlank
        String justificacion
) {}

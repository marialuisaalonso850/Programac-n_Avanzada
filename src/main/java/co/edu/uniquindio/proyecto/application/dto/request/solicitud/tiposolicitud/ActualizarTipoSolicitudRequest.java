package co.edu.uniquindio.proyecto.application.dto.request.solicitud.tiposolicitud;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ActualizarTipoSolicitudRequest(
        @NotNull(message = "El ID es obligatorio para realizar la actualización")
        Long id,

        @NotBlank(message = "El nombre del tipo de solicitud es obligatorio")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria para dar contexto al tipo de solicitud")
        @Size(min = 10, message = "La descripción debe ser más detallada (mínimo 10 caracteres)")
        String descripcion
) {}
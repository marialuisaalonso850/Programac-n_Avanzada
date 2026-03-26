package co.edu.uniquindio.proyecto.application.dto.request.solicitud.tiposolicitud;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ActualizarTipoSolicitudRequest(
        @NotNull(message = "El ID es obligatorio para realizar la actualización")
        Long id,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 50)
        String nombre,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion
) {}

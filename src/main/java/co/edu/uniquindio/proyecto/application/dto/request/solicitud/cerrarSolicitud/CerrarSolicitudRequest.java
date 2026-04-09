package co.edu.uniquindio.proyecto.application.dto.request.solicitud.cerrarSolicitud;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CerrarSolicitudRequest(
        @NotBlank(message = "La observación de cierre es obligatoria")
        @Size(min = 10, max = 500, message = "La observación debe tener entre 10 y 500 caracteres")
        String observacion
) {}
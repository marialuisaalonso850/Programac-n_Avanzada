package co.edu.uniquindio.proyecto.application.dto.request.solicitud.cerrarSolicitud;

import jakarta.validation.constraints.NotBlank;

public record CerrarSolicitudRequest(
        @NotBlank(message = "La observación de cierre es obligatoria")
        String observacion
) {}
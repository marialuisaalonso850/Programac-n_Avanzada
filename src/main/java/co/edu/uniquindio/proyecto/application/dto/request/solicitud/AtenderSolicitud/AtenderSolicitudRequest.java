package co.edu.uniquindio.proyecto.application.dto.request.solicitud.AtenderSolicitud;

import jakarta.validation.constraints.NotBlank;

public record AtenderSolicitudRequest(
        @NotBlank(message = "La observación es obligatoria")
        String observacion
) {}

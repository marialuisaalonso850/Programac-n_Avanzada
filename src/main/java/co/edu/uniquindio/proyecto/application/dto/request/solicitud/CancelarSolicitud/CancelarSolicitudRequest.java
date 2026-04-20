package co.edu.uniquindio.proyecto.application.dto.request.solicitud.CancelarSolicitud;

import jakarta.validation.constraints.NotBlank;

public record CancelarSolicitudRequest(
        @NotBlank(message = "El motivo de cancelación es obligatorio")
        String motivo
) {}
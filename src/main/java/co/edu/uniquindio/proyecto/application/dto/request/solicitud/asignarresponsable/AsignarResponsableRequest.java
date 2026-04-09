package co.edu.uniquindio.proyecto.application.dto.request.solicitud.asignarresponsable;

import jakarta.validation.constraints.NotBlank;

public record AsignarResponsableRequest(
        @NotBlank(message = "El ID del responsable es obligatorio")
        String responsableId
) {}
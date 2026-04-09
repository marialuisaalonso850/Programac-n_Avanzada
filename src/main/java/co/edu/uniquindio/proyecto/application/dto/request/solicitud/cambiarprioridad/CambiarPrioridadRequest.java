package co.edu.uniquindio.proyecto.application.dto.request.solicitud.cambiarprioridad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CambiarPrioridadRequest(

        @NotBlank(message = "La nueva prioridad es obligatoria")
        String nuevaPrioridad,

        @NotBlank(message = "El ID del coordinador es obligatorio")
        String coordinadorId,

        @NotBlank(message = "Debe proporcionar una justificación para el cambio")
        @Size(min = 10, max = 500, message = "La justificación debe tener entre 10 y 500 caracteres")
        String justificacion
) {}
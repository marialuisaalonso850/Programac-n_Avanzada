package co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import jakarta.validation.constraints.*;

public record ClasificarSolicitudRequest(

        // Quitamos @NotNull para que sea opcional
        TipoSolicitud tipoSolicitud,

        // Quitamos @NotNull para que sea opcional
        Prioridad prioridad,

        // Este SI es obligatorio
        @NotBlank(message = "La justificación es obligatoria")
        @Size(min = 10, max = 500, message = "La justificación debe tener entre 10 y 500 caracteres")
        String justificacion
) {}
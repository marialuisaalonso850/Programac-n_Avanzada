package co.edu.uniquindio.proyecto.application.dto.request.solicitud.crearsolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.CanalOrigen;
import jakarta.validation.constraints.*;

public record CrearSolicitudRequest(
        @NotNull(message = "El tipo de solicitud es obligatorio")
        Long tipoSolicitudId,
        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 20, max = 1000,
                message = "La descripción debe tener entre 20 y 1000 caracteres")
        String descripcion,
        @NotNull(message = "El canal de origen es obligatorio")
        CanalOrigen canalOrigen
) {}
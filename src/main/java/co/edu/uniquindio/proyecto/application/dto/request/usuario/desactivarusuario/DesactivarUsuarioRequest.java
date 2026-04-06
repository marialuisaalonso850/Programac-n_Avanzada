package co.edu.uniquindio.proyecto.application.dto.request.usuario.desactivarusuario;
import jakarta.validation.constraints.NotNull;

public record DesactivarUsuarioRequest(
        @NotNull(message = "La identificación es obligatoria")
        String identificacion,

        @NotNull(message = "El estado de solicitudes es requerido para la validación")
        boolean tieneSolicitudesActivas
) {}
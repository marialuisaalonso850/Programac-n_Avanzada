package co.edu.uniquindio.proyecto.application.dto.request.solicitud.tiposolicitud;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación de un nuevo Tipo de Solicitud.
 * Refleja las propiedades esenciales de la entidad.
 */
public record CrearTipoSolicitudRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion
) {}

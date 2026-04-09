package co.edu.uniquindio.proyecto.application.dto.response.solicitud.tiposolicitud;

/**
 * DTO de salida que representa cómo se verá un Tipo de Solicitud en la API.
 */
public record TipoSolicitudResponse(
        Long id,
        String nombre,
        String descripcion
) {}

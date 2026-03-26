package co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta;

/**
 * DTO resumido de usuario (evita exponer todo el objeto Usuario)
 */
public record UsuarioResumenDTO(
        String id,
        String nombreCompleto,
        String email,
        String rol
) {}

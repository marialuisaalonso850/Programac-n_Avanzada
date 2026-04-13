package co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud;

import java.util.List;

public record PaginaSolicitudes(
        List<SolicitudResumenResponse> contenido,
        int paginaActual,
        int totalPaginas
) {}
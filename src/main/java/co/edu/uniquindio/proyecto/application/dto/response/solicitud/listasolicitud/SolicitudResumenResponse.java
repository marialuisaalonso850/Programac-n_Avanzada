package co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud;

import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;

import java.time.LocalDateTime;

public record SolicitudResumenResponse(
        String id,
        String codigo,
        String tipoNombre,
        String descripcionBreve,
        EstadoSolicitud estado,
        Prioridad nivelPrioridad,
        String solicitanteNombre,
        String responsableNombre,
        LocalDateTime fechaCreacion
) {}

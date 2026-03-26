package co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud;

import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;

import java.time.LocalDateTime;

public record SolicitudResumenResponse(
        String id,
        String codigo,
        String tipoNombre, // Solo el nombre, no el objeto completo
        String descripcionBreve, // Primeros 100 caracteres
        EstadoSolicitud estado,
        Prioridad nivelPrioridad,
        String solicitanteNombre,
        String responsableNombre, // puede ser null
        LocalDateTime fechaCreacion
) {}

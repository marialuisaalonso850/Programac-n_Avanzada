package co.edu.uniquindio.proyecto.application.dto.response.solicitud.historial;

import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoAccion;

import java.time.LocalDateTime;

public record EventoHistorialResponse(
        Long secuencia,
        LocalDateTime fechaHora,
        TipoAccion accion,
        String usuarioNombre, // Nombre del usuario que realizó la acción
        String observacion,
        EstadoSolicitud estadoAnterior, // puede ser null
        EstadoSolicitud estadoNuevo // puede ser null
) {}
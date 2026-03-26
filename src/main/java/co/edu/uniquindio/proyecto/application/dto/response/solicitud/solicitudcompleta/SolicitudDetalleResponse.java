package co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud.PrioridadDTO;
import co.edu.uniquindio.proyecto.domain.valueobject.CanalOrigen;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;

import java.time.LocalDateTime;

public record SolicitudDetalleResponse(
        String id,
        String codigo, // SOL-2024-00123
        TipoSolicitudDTO tipo,
        String descripcion,
        CanalOrigen canalOrigen,
        EstadoSolicitud estado,
        PrioridadDTO prioridad,
        UsuarioResumenDTO solicitante,
        UsuarioResumenDTO responsable, // puede ser null
        LocalDateTime fechaCreacion,
        LocalDateTime fechaUltimaActualizacion,
        int totalEventosHistorial
) {}

package co.edu.uniquindio.proyecto.infraestructure.rest.mapper;

import co.edu.uniquindio.proyecto.application.dto.response.solicitud.historial.EventoHistorialResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.SolicitudResumenResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.SolicitudDetalleResponse;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "usuario.nombre", target = "solicitante.nombreCompleto")
    @Mapping(source = "usuario.identificacion", target = "solicitante.id")
    @Mapping(source = "usuario.email", target = "solicitante.email")
    @Mapping(source = "responsable.nombre", target = "responsable.nombreCompleto")
    @Mapping(source = "responsable.identificacion", target = "responsable.id")
    @Mapping(source = "responsable.email", target = "responsable.email")
    SolicitudDetalleResponse toDetalleResponse(Solicitud solicitud);

    @Mapping(source = "id", target = "id")
    SolicitudResumenResponse toResumenResponse(Solicitud solicitud);

    @Mapping(source = "usuarioId", target = "usuarioNombre")
    EventoHistorialResponse toHistorialResponse(Historial historial);

    // Listas se mapean automáticamente usando los métodos individuales de arriba
    List<SolicitudDetalleResponse> toResponseList(List<Solicitud> solicitudes);
    List<SolicitudResumenResponse> toResumenResponseList(List<Solicitud> solicitudes);
    List<EventoHistorialResponse> toHistorialResponseList(List<Historial> historial);

    // --- MÉTODOS DE SOPORTE (Traducción de Value Objects a String) ---
    // MapStruct usará estos métodos automáticamente cuando necesite convertir el objeto a String

    default String map(SolicitudId id) {
        return (id != null && id.getValue() != null) ? id.getValue().toString() : null;
    }

    default String map(Descripcion d) {
        return (d != null) ? d.valor() : null; // Usa el getter real de tu clase Descripcion
    }

    default String map(Usuario n) {
        return (n != null) ? n.getNombre() : null; // Usa el getter real de tu clase Nombre
    }

    default String map(DocumentoIdentidad doc) {
        return (doc != null) ? doc.numero() : null; // Usa el getter real (numero o valor)
    }

    default String map(Email email) {
        return (email != null) ? email.valor() : null;
    }
}
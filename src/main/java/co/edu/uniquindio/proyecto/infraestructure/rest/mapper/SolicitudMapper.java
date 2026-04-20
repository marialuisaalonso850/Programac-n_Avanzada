package co.edu.uniquindio.proyecto.infraestructure.rest.mapper;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud.PrioridadDTO;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.historial.EventoHistorialResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.SolicitudResumenResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.SolicitudDetalleResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.TipoSolicitudDTO;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.UsuarioResumenDTO;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Historial;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.SolicitudEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {
        UUID.class, SolicitudId.class, Descripcion.class, Prioridad.class,
        EstadoSolicitud.class, CanalOrigen.class, TipoSolicitud.class, LocalDateTime.class
})
public interface SolicitudMapper {

    // --- 1. PERSISTENCIA: De la Base de Datos al Dominio ---
    @Mapping(target = "id", expression = "java(new SolicitudId(UUID.fromString(entity.getId())))")
    @Mapping(target = "descripcion", expression = "java(new Descripcion(entity.getDescripcion()))")
    @Mapping(target = "prioridad", expression = "java(entity.getPrioridad() != null ? Prioridad.valueOf(entity.getPrioridad()) : null)")
    @Mapping(target = "estado", expression = "java(entity.getEstado() != null ? EstadoSolicitud.valueOf(entity.getEstado()) : null)")
    @Mapping(target = "canal", expression = "java(entity.getCanal() != null ? CanalOrigen.valueOf(entity.getCanal()) : null)")
    @Mapping(target = "tipo", expression = "java(entity.getTipo() != null ? TipoSolicitud.valueOf(entity.getTipo()) : null)")
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "responsable", ignore = true)
    @Mapping(target = "historial", ignore = true)
    Solicitud toDomain(SolicitudEntity entity);

    // --- 2. PERSISTENCIA: Del Dominio a la Base de Datos ---
    @Mapping(target = "id", expression = "java(solicitud.getId().value().toString())")
    @Mapping(target = "descripcion", expression = "java(solicitud.getDescripcion().valor())")
    @Mapping(source = "usuario.identificacion.numero", target = "solicitanteId")
    @Mapping(source = "responsable.identificacion.numero", target = "responsableId")
    @Mapping(target = "estado", expression = "java(solicitud.getEstado() != null ? solicitud.getEstado().name() : null)")
    @Mapping(target = "prioridad", expression = "java(solicitud.getPrioridad() != null ? solicitud.getPrioridad().name() : null)")
    @Mapping(target = "canal", expression = "java(solicitud.getCanal() != null ? solicitud.getCanal().name() : null)")
    @Mapping(target = "tipo", expression = "java(solicitud.getTipo() != null ? solicitud.getTipo().name() : null)")
    @Mapping(source = "fechaCreacion", target = "fechaCreacion")
    SolicitudEntity toEntity(Solicitud solicitud);

    // --- 3. API REST: Detalle Completo ---
    default SolicitudDetalleResponse toDetalleResponse(Solicitud solicitud) {
        if (solicitud == null) return null;

        return new SolicitudDetalleResponse(
                solicitud.getId().value().toString(),
                solicitud.getId().value().toString(),
                mapearTipoADto(solicitud.getTipo()),
                mapDescripcionToString(solicitud.getDescripcion()),
                solicitud.getCanal(),
                solicitud.getEstado(),
                new PrioridadDTO(solicitud.getPrioridad(), null),
                solicitud.getUsuario() != null ? new UsuarioResumenDTO(
                        solicitud.getUsuario().getIdentificacion().numero(),
                        solicitud.getUsuario().getNombre(),
                        solicitud.getUsuario().getEmail().valor(),
                        solicitud.getUsuario().getTipoUsuario().name()
                ) : null,
                solicitud.getResponsable() != null ? new UsuarioResumenDTO(
                        solicitud.getResponsable().getIdentificacion().numero(),
                        solicitud.getResponsable().getNombre(),
                        solicitud.getResponsable().getEmail().valor(),
                        null
                ) : null,
                solicitud.getFechaCreacion(),
                LocalDateTime.now(),
                solicitud.getHistorial() != null ? solicitud.getHistorial().size() : 0
        );
    }

    // --- 4. API REST: Resumen / Lista ---
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id.value", target = "codigo")
    @Mapping(source = "tipo", target = "tipoNombre")
    @Mapping(target = "descripcionBreve", expression = "java(mapDescripcionToString(solicitud.getDescripcion()))")
    @Mapping(source = "prioridad", target = "nivelPrioridad")
    @Mapping(source = "usuario.nombre", target = "solicitanteNombre")
    @Mapping(source = "responsable.nombre", target = "responsableNombre")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "fechaCreacion", target = "fechaCreacion")
    SolicitudResumenResponse toResumenResponse(Solicitud solicitud);

    List<SolicitudResumenResponse> toResumenResponseList(List<Solicitud> solicitudes);

    // --- 5. HISTORIAL ---
    default EventoHistorialResponse toHistorialResponse(Historial historial) {
        if (historial == null) return null;
        return new EventoHistorialResponse(
                null,                           // secuencia
                historial.fechaHora(),          // fechaHora
                historial.accion(),             // accion
                historial.usuarioId().numero(), // usuarioNombre
                historial.observacion(),        // observacion
                null,                           // estadoAnterior
                null                            // estadoNuevo
        );
    }

    List<EventoHistorialResponse> toHistorialResponseList(List<Historial> historial);

    // --- 6. MÉTODOS DE APOYO ---
    default String mapDocumentoToString(DocumentoIdentidad doc) {
        if (doc == null) return null;
        return doc.numero();
    }

    default String mapEmailToString(Email email) {
        if (email == null) return null;
        return email.valor();
    }

    default String mapDescripcionToString(Descripcion descripcion) {
        if (descripcion == null) return null;
        return descripcion.valor();
    }

    default TipoSolicitudDTO mapearTipoADto(TipoSolicitud tipo) {
        if (tipo == null) return null;
        return new TipoSolicitudDTO((long) tipo.ordinal() + 1, tipo.name(), tipo.name().replace("_", " "));
    }
}

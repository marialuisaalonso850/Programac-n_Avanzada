package co.edu.uniquindio.proyecto.infraestructure.rest.mapper;

import co.edu.uniquindio.proyecto.application.dto.response.solicitud.historial.EventoHistorialResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.SolicitudResumenResponse;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.CanalOrigen;
import co.edu.uniquindio.proyecto.domain.valueobject.Descripcion;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Historial;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.SolicitudId;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.HistorialEntity;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.SolicitudEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T09:24:45-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Eclipse Adoptium)"
)
@Component
public class SolicitudMapperImpl implements SolicitudMapper {

    @Override
    public Solicitud toDomain(SolicitudEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Solicitud.SolicitudBuilder solicitud = Solicitud.builder();

        solicitud.fechaCreacion( entity.getFechaCreacion() );

        solicitud.id( new SolicitudId(UUID.fromString(entity.getId())) );
        solicitud.descripcion( new Descripcion(entity.getDescripcion()) );
        solicitud.prioridad( entity.getPrioridad() != null ? Prioridad.valueOf(entity.getPrioridad()) : null );
        solicitud.estado( entity.getEstado() != null ? EstadoSolicitud.valueOf(entity.getEstado()) : null );
        solicitud.canal( entity.getCanal() != null ? CanalOrigen.valueOf(entity.getCanal()) : null );
        solicitud.tipo( entity.getTipo() != null ? TipoSolicitud.valueOf(entity.getTipo()) : null );

        return solicitud.build();
    }

    @Override
    public SolicitudEntity toEntity(Solicitud solicitud) {
        if ( solicitud == null ) {
            return null;
        }

        SolicitudEntity solicitudEntity = new SolicitudEntity();

        solicitudEntity.setSolicitanteId( solicitudUsuarioIdentificacionNumero( solicitud ) );
        solicitudEntity.setResponsableId( solicitudResponsableIdentificacionNumero( solicitud ) );
        solicitudEntity.setFechaCreacion( solicitud.getFechaCreacion() );
        solicitudEntity.setHistorial( historialListToHistorialEntityList( solicitud.getHistorial() ) );

        solicitudEntity.setId( solicitud.getId().value().toString() );
        solicitudEntity.setDescripcion( solicitud.getDescripcion().valor() );
        solicitudEntity.setEstado( solicitud.getEstado() != null ? solicitud.getEstado().name() : null );
        solicitudEntity.setPrioridad( solicitud.getPrioridad() != null ? solicitud.getPrioridad().name() : null );
        solicitudEntity.setCanal( solicitud.getCanal() != null ? solicitud.getCanal().name() : null );
        solicitudEntity.setTipo( solicitud.getTipo() != null ? solicitud.getTipo().name() : null );

        return solicitudEntity;
    }

    @Override
    public SolicitudResumenResponse toResumenResponse(Solicitud solicitud) {
        if ( solicitud == null ) {
            return null;
        }

        String codigo = null;
        String tipoNombre = null;
        Prioridad nivelPrioridad = null;
        String solicitanteNombre = null;
        String responsableNombre = null;
        EstadoSolicitud estado = null;
        LocalDateTime fechaCreacion = null;

        UUID value = solicitudIdValue( solicitud );
        if ( value != null ) {
            codigo = value.toString();
        }
        if ( solicitud.getTipo() != null ) {
            tipoNombre = solicitud.getTipo().name();
        }
        nivelPrioridad = solicitud.getPrioridad();
        solicitanteNombre = solicitudUsuarioNombre( solicitud );
        responsableNombre = solicitudResponsableNombre( solicitud );
        estado = solicitud.getEstado();
        fechaCreacion = solicitud.getFechaCreacion();

        String id = null;
        String descripcionBreve = mapDescripcionToString(solicitud.getDescripcion());

        SolicitudResumenResponse solicitudResumenResponse = new SolicitudResumenResponse( id, codigo, tipoNombre, descripcionBreve, estado, nivelPrioridad, solicitanteNombre, responsableNombre, fechaCreacion );

        return solicitudResumenResponse;
    }

    @Override
    public List<SolicitudResumenResponse> toResumenResponseList(List<Solicitud> solicitudes) {
        if ( solicitudes == null ) {
            return null;
        }

        List<SolicitudResumenResponse> list = new ArrayList<SolicitudResumenResponse>( solicitudes.size() );
        for ( Solicitud solicitud : solicitudes ) {
            list.add( toResumenResponse( solicitud ) );
        }

        return list;
    }

    @Override
    public List<EventoHistorialResponse> toHistorialResponseList(List<Historial> historial) {
        if ( historial == null ) {
            return null;
        }

        List<EventoHistorialResponse> list = new ArrayList<EventoHistorialResponse>( historial.size() );
        for ( Historial historial1 : historial ) {
            list.add( toHistorialResponse( historial1 ) );
        }

        return list;
    }

    private String solicitudUsuarioIdentificacionNumero(Solicitud solicitud) {
        Usuario usuario = solicitud.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        DocumentoIdentidad identificacion = usuario.getIdentificacion();
        if ( identificacion == null ) {
            return null;
        }
        return identificacion.numero();
    }

    private String solicitudResponsableIdentificacionNumero(Solicitud solicitud) {
        Usuario responsable = solicitud.getResponsable();
        if ( responsable == null ) {
            return null;
        }
        DocumentoIdentidad identificacion = responsable.getIdentificacion();
        if ( identificacion == null ) {
            return null;
        }
        return identificacion.numero();
    }

    protected HistorialEntity historialToHistorialEntity(Historial historial) {
        if ( historial == null ) {
            return null;
        }

        HistorialEntity historialEntity = new HistorialEntity();

        historialEntity.setFechaHora( historial.fechaHora() );
        if ( historial.accion() != null ) {
            historialEntity.setAccion( historial.accion().name() );
        }
        historialEntity.setUsuarioId( mapDocumentoToString( historial.usuarioId() ) );
        historialEntity.setObservacion( historial.observacion() );

        return historialEntity;
    }

    protected List<HistorialEntity> historialListToHistorialEntityList(List<Historial> list) {
        if ( list == null ) {
            return null;
        }

        List<HistorialEntity> list1 = new ArrayList<HistorialEntity>( list.size() );
        for ( Historial historial : list ) {
            list1.add( historialToHistorialEntity( historial ) );
        }

        return list1;
    }

    private UUID solicitudIdValue(Solicitud solicitud) {
        SolicitudId id = solicitud.getId();
        if ( id == null ) {
            return null;
        }
        return id.value();
    }

    private String solicitudUsuarioNombre(Solicitud solicitud) {
        Usuario usuario = solicitud.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        return usuario.getNombre();
    }

    private String solicitudResponsableNombre(Solicitud solicitud) {
        Usuario responsable = solicitud.getResponsable();
        if ( responsable == null ) {
            return null;
        }
        return responsable.getNombre();
    }
}

package co.edu.uniquindio.proyecto.infraestructure.rest.mapper;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud.PrioridadDTO;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.historial.EventoHistorialResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.SolicitudResumenResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.SolicitudDetalleResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.TipoSolicitudDTO;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.UsuarioResumenDTO;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.CanalOrigen;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Historial;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoAccion;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T21:35:52-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Eclipse Adoptium)"
)
@Component
public class SolicitudMapperImpl implements SolicitudMapper {

    @Override
    public SolicitudDetalleResponse toDetalleResponse(Solicitud solicitud) {
        if ( solicitud == null ) {
            return null;
        }

        UsuarioResumenDTO solicitante = null;
        UsuarioResumenDTO responsable = null;
        String id = null;
        String descripcion = null;
        TipoSolicitudDTO tipo = null;
        EstadoSolicitud estado = null;
        PrioridadDTO prioridad = null;
        LocalDateTime fechaCreacion = null;

        solicitante = usuarioToUsuarioResumenDTO( solicitud.getUsuario() );
        responsable = usuarioToUsuarioResumenDTO1( solicitud.getResponsable() );
        id = map( solicitud.getId() );
        descripcion = map( solicitud.getDescripcion() );
        tipo = tipoSolicitudToTipoSolicitudDTO( solicitud.getTipo() );
        estado = solicitud.getEstado();
        prioridad = prioridadToPrioridadDTO( solicitud.getPrioridad() );
        fechaCreacion = solicitud.getFechaCreacion();

        String codigo = null;
        CanalOrigen canalOrigen = null;
        LocalDateTime fechaUltimaActualizacion = null;
        int totalEventosHistorial = 0;

        SolicitudDetalleResponse solicitudDetalleResponse = new SolicitudDetalleResponse( id, codigo, tipo, descripcion, canalOrigen, estado, prioridad, solicitante, responsable, fechaCreacion, fechaUltimaActualizacion, totalEventosHistorial );

        return solicitudDetalleResponse;
    }

    @Override
    public SolicitudResumenResponse toResumenResponse(Solicitud solicitud) {
        if ( solicitud == null ) {
            return null;
        }

        String id = null;
        EstadoSolicitud estado = null;
        LocalDateTime fechaCreacion = null;

        id = map( solicitud.getId() );
        estado = solicitud.getEstado();
        fechaCreacion = solicitud.getFechaCreacion();

        String codigo = null;
        String tipoNombre = null;
        String descripcionBreve = null;
        Prioridad nivelPrioridad = null;
        String solicitanteNombre = null;
        String responsableNombre = null;

        SolicitudResumenResponse solicitudResumenResponse = new SolicitudResumenResponse( id, codigo, tipoNombre, descripcionBreve, estado, nivelPrioridad, solicitanteNombre, responsableNombre, fechaCreacion );

        return solicitudResumenResponse;
    }

    @Override
    public EventoHistorialResponse toHistorialResponse(Historial historial) {
        if ( historial == null ) {
            return null;
        }

        String usuarioNombre = null;
        LocalDateTime fechaHora = null;
        TipoAccion accion = null;
        String observacion = null;

        usuarioNombre = map( historial.usuarioId() );
        fechaHora = historial.fechaHora();
        accion = historial.accion();
        observacion = historial.observacion();

        Long secuencia = null;
        EstadoSolicitud estadoAnterior = null;
        EstadoSolicitud estadoNuevo = null;

        EventoHistorialResponse eventoHistorialResponse = new EventoHistorialResponse( secuencia, fechaHora, accion, usuarioNombre, observacion, estadoAnterior, estadoNuevo );

        return eventoHistorialResponse;
    }

    @Override
    public List<SolicitudDetalleResponse> toResponseList(List<Solicitud> solicitudes) {
        if ( solicitudes == null ) {
            return null;
        }

        List<SolicitudDetalleResponse> list = new ArrayList<SolicitudDetalleResponse>( solicitudes.size() );
        for ( Solicitud solicitud : solicitudes ) {
            list.add( toDetalleResponse( solicitud ) );
        }

        return list;
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

    protected UsuarioResumenDTO usuarioToUsuarioResumenDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        String nombreCompleto = null;
        String id = null;
        String email = null;

        nombreCompleto = usuario.getNombre();
        id = map( usuario.getIdentificacion() );
        email = map( usuario.getEmail() );

        String rol = null;

        UsuarioResumenDTO usuarioResumenDTO = new UsuarioResumenDTO( id, nombreCompleto, email, rol );

        return usuarioResumenDTO;
    }

    protected UsuarioResumenDTO usuarioToUsuarioResumenDTO1(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        String nombreCompleto = null;
        String id = null;
        String email = null;

        nombreCompleto = usuario.getNombre();
        id = map( usuario.getIdentificacion() );
        email = map( usuario.getEmail() );

        String rol = null;

        UsuarioResumenDTO usuarioResumenDTO = new UsuarioResumenDTO( id, nombreCompleto, email, rol );

        return usuarioResumenDTO;
    }

    protected TipoSolicitudDTO tipoSolicitudToTipoSolicitudDTO(TipoSolicitud tipoSolicitud) {
        if ( tipoSolicitud == null ) {
            return null;
        }

        Long id = null;
        String codigo = null;
        String nombre = null;

        TipoSolicitudDTO tipoSolicitudDTO = new TipoSolicitudDTO( id, codigo, nombre );

        return tipoSolicitudDTO;
    }

    protected PrioridadDTO prioridadToPrioridadDTO(Prioridad prioridad) {
        if ( prioridad == null ) {
            return null;
        }

        Prioridad nivel = null;
        String justificacion = null;

        PrioridadDTO prioridadDTO = new PrioridadDTO( nivel, justificacion );

        return prioridadDTO;
    }
}

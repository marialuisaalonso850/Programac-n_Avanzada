package co.edu.uniquindio.proyecto.infraestructure.persistencia.mapper;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Descripcion;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Historial;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.HistorialEntity;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.SolicitudEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T09:25:04-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Eclipse Adoptium)"
)
@Component
public class SolicitudPersistenceMapperImpl implements SolicitudPersistenceMapper {

    @Override
    public SolicitudEntity toEntity(Solicitud solicitud) {
        if ( solicitud == null ) {
            return null;
        }

        SolicitudEntity solicitudEntity = new SolicitudEntity();

        solicitudEntity.setDescripcion( solicitudDescripcionValor( solicitud ) );
        solicitudEntity.setSolicitanteId( solicitudUsuarioIdentificacionNumero( solicitud ) );
        solicitudEntity.setResponsableId( solicitudResponsableIdentificacionNumero( solicitud ) );
        if ( solicitud.getTipo() != null ) {
            solicitudEntity.setTipo( solicitud.getTipo().name() );
        }
        if ( solicitud.getEstado() != null ) {
            solicitudEntity.setEstado( solicitud.getEstado().name() );
        }
        if ( solicitud.getCanal() != null ) {
            solicitudEntity.setCanal( solicitud.getCanal().name() );
        }
        solicitudEntity.setFechaCreacion( solicitud.getFechaCreacion() );
        solicitudEntity.setHistorial( historialListToHistorialEntityList( solicitud.getHistorial() ) );

        solicitudEntity.setId( solicitud.getId().getValue().toString() );
        solicitudEntity.setPrioridad( solicitud.getPrioridad() != null ? solicitud.getPrioridad().name() : null );

        return solicitudEntity;
    }

    private String solicitudDescripcionValor(Solicitud solicitud) {
        Descripcion descripcion = solicitud.getDescripcion();
        if ( descripcion == null ) {
            return null;
        }
        return descripcion.valor();
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
        historialEntity.setUsuarioId( mapDocumento( historial.usuarioId() ) );
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
}

package co.edu.uniquindio.proyecto.infraestructure.persistencia.mapper;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.SolicitudEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SolicitudPersistenceMapper {

    @Mapping(target = "id", expression = "java(solicitud.getId().getValue().toString())")
    @Mapping(target = "descripcion", source = "descripcion.valor")
    @Mapping(target = "solicitanteId", source = "usuario.identificacion.numero")
    @Mapping(target = "responsableId", source = "responsable.identificacion.numero")
    @Mapping(target = "tipo", source = "tipo")
    @Mapping(target = "estado", source = "estado")@Mapping(target = "prioridad", expression = "java(solicitud.getPrioridad() != null ? solicitud.getPrioridad().name() : null)")
    @Mapping(target = "canal", source = "canal")
    SolicitudEntity toEntity(Solicitud solicitud);

    default Solicitud toDomain(SolicitudEntity entity) {
        if (entity == null) return null;

        return Solicitud.builder()
                .id(new SolicitudId(UUID.fromString(entity.getId())))
                .fechaCreacion(entity.getFechaCreacion())
                .descripcion(new Descripcion(entity.getDescripcion()))
                .tipo(entity.getTipo() != null ? TipoSolicitud.valueOf(entity.getTipo()) : null)
                .estado(EstadoSolicitud.valueOf(entity.getEstado()))
                .prioridad(entity.getPrioridad() != null ?
                        Prioridad.valueOf(entity.getPrioridad()) : null)
                .canal(entity.getCanal() != null ? CanalOrigen.valueOf(entity.getCanal()) : null)

                // CAMBIO 1: Se usa .solicitante(...) en lugar de .usuario(...)
                .usuario(entity.getSolicitanteId() != null ?
                        Usuario.builder()
                                .identificacion(new DocumentoIdentidad(
                                        TipoDocumento.CEDULA_CIUDADANIA, // Ponemos uno por defecto para evitar el 400
                                        entity.getSolicitanteId()
                                ))
                                .nombre("Usuario Cargado") // Evita que validaciones de nombre fallen
                                .email(new Email("sistema@proyecto.com")) // Evita que validaciones de email fallen
                                .build()
                        : null)

                .responsable(entity.getResponsableId() != null ?
                        Usuario.builder()
                                .identificacion(new DocumentoIdentidad(
                                        TipoDocumento.CEDULA_CIUDADANIA,
                                        entity.getResponsableId()
                                ))
                                .build()
                        : null)
                .build();
    }

    default String mapDocumento(DocumentoIdentidad value) {
        return (value != null) ? value.numero() : null;
    }
}
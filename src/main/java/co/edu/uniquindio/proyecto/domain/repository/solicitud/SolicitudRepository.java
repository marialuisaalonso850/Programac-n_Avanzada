package co.edu.uniquindio.proyecto.domain.repository.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.SolicitudId;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository {

    Solicitud save(Solicitud solicitud);

    Optional<Solicitud> findById(String id);

    List<Solicitud> findAll();

    List<Solicitud> findByEstado(EstadoSolicitud estado);

    List<Solicitud> findBySolicitanteIdentificacion(DocumentoIdentidad usuarioId);

    List<Solicitud> findByResponsableIdentificacion(DocumentoIdentidad responsableId);
}
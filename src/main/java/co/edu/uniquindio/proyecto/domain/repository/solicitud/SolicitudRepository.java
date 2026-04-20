package co.edu.uniquindio.proyecto.domain.repository.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SolicitudRepository {

    void save(Solicitud solicitud);
    Optional<Solicitud> findById(String id);

    Page<Solicitud> findByFiltros(
            EstadoSolicitud estado,
            Prioridad prioridad,
            String solicitanteId,
            Pageable pageable
    );

    // nuevo
    boolean existeSolicitudActivaPorUsuario(DocumentoIdentidad id);
}
package co.edu.uniquindio.proyecto.domain.repository.solicitud;


import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.SolicitudId;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository {

    void guardar(Solicitud solicitud);

    Optional<Solicitud> obtenerPorId(SolicitudId id);

    List<Solicitud> obtenerPorEstado(EstadoSolicitud estado);
}

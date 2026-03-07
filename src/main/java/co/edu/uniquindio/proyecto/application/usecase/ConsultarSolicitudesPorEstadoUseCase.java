package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;

import java.util.List;

public class ConsultarSolicitudesPorEstadoUseCase {

    private final SolicitudRepository repository;

    public ConsultarSolicitudesPorEstadoUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public List<Solicitud> ejecutar(EstadoSolicitud estado) {
        return repository.obtenerPorEstado(estado);
    }
}
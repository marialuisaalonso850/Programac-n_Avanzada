package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.SolicitudId;

public class CerrarSolicitudUseCase {

    private final SolicitudRepository repository;

    public CerrarSolicitudUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public void ejecutar(SolicitudId id, String observacion) {

        Solicitud solicitud = repository.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.cerrar(observacion);

        repository.guardar(solicitud);
    }
}
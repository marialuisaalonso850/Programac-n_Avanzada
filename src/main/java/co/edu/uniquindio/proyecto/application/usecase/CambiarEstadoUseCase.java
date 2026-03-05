package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.*;

public class CambiarEstadoUseCase {

    private final SolicitudRepository repository;

    public CambiarEstadoUseCase(SolicitudRepository repository) {
        this.repository = repository;
    }

    public void ejecutar(SolicitudId id,
                         EstadoSolicitud nuevoEstado,
                         String observacion,
                         Usuario usuario) {

        Solicitud solicitud = repository.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.cambiarEstado(nuevoEstado, observacion, usuario);

        repository.guardar(solicitud);
    }
}
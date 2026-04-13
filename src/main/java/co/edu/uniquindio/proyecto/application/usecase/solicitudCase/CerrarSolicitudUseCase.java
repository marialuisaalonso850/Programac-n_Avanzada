package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.SolicitudId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CerrarSolicitudUseCase {

    private final SolicitudRepository repository;

    @Transactional
    public void ejecutar(SolicitudId id, String observacion) {

        Solicitud solicitud = repository.findById(id.getValue())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.cerrar(observacion);

        repository.save(solicitud);
    }
}
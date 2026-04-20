package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.EntidadNoEncontradaException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarcarAtendidaUseCase {

    private final SolicitudRepository repository;

    @Transactional
    public Solicitud ejecutar(String id, String observacion) {

        Solicitud solicitud = repository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "Solicitud no encontrada: " + id));

        solicitud.marcarComoAtendida(observacion);
        repository.save(solicitud);
        return solicitud;
    }
}
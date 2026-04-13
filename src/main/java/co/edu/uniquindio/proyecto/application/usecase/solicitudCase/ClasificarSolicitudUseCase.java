package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud.ClasificarSolicitudRequest;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.SolicitudId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClasificarSolicitudUseCase {

    private final SolicitudRepository repository;

    @Transactional
    public Solicitud ejecutar(String id, ClasificarSolicitudRequest request) {

        Solicitud solicitud = repository.findById(id)
                .orElseThrow(() -> new ReglaDominioException("Solicitud no encontrada"));

        solicitud.cambiarPrioridad(
                request.prioridad(),
                solicitud.getUsuario(),
                request.justificacion()
        );

        return repository.save(solicitud);
    }
}
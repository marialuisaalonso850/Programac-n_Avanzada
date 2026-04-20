package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CerrarSolicitudService {
    private final SolicitudRepository solicitudRepository;

    public void ejecutar(Solicitud solicitud, Usuario usuarioActual, String observacion) {
        if (!usuarioActual.equals(solicitud.getResponsable()) && !usuarioActual.esAdmin()) {
            throw new ReglaDominioException("Solo el responsable puede cerrar la solicitud");
        }
        solicitud.cerrar(observacion);
        solicitudRepository.save(solicitud);
    }
}


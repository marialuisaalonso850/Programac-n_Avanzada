package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CancelarSolicitudService {

    private final SolicitudRepository solicitudRepository;

    public void ejecutar(Solicitud solicitud, Usuario usuarioActual, String observacion) {

        boolean esDuenio = solicitud.getUsuario().getIdentificacion()
                .equals(usuarioActual.getIdentificacion());

        if (!esDuenio && !usuarioActual.esAdmin()) {
            throw new ReglaDominioException("No está autorizado para cancelar esta solicitud.");
        }

        solicitud.cancelar(usuarioActual, observacion);


        solicitudRepository.save(solicitud);
    }
}
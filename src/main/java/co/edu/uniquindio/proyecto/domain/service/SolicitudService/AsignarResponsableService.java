package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AsignarResponsableService {

    private final SolicitudRepository solicitudRepository;

    public void ejecutar(Solicitud solicitud, Usuario usuarioActual, Usuario responsable) {

        if (!usuarioActual.esAdmin() && !usuarioActual.esCoordinador()) {
            throw new ReglaDominioException("Solo un administrador o coordinador puede asignar responsable");
        }

        if (!responsable.puedeSerResponsable()) {
            throw new ReglaDominioException("El usuario seleccionado no puede ser responsable");
        }
        solicitud.asignarResponsable(responsable, usuarioActual);

        solicitudRepository.save(solicitud);
    }
}
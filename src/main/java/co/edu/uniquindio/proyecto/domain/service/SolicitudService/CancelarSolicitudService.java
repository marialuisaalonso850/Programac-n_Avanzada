package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class CancelarSolicitudService {
    public void ejecutar(Solicitud solicitud, Usuario usuarioActual, String observacion) {
        if (!usuarioActual.equals(solicitud.getUsuario()) && !usuarioActual.esAdmin()) {
            throw new ReglaDominioException("No autorizado para cancelar");
        }
        solicitud.cancelar(observacion, usuarioActual);
    }
}


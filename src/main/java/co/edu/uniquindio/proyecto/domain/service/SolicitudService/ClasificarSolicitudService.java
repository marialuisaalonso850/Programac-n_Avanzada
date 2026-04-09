package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

public class ClasificarSolicitudService {
    public void ejecutar(Solicitud solicitud, TipoSolicitud nuevoTipo, Usuario usuarioActual, String observacion) {
        if (!usuarioActual.esAdmin() && !usuarioActual.esCoordinador()) {
            throw new ReglaDominioException("Solo un administrador o coordinador puede clasificar");
        }
        solicitud.clasificar(nuevoTipo, usuarioActual, observacion);
    }
}

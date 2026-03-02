package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class ClasificarSolicitudService {

    public void ejecutar(
            Solicitud solicitud,
            TipoSolicitud nuevoTipo,
            Usuario usuarioActual,
            String observacion
    ) {

        if (!usuarioActual.esAdmin()
                && !usuarioActual.esCoordinador()) {

            throw new ReglaDominioException(
                    "Solo un administrador o coordinador puede clasificar"
            );
        }

        solicitud.clasificar(nuevoTipo, usuarioActual, observacion);
    }
}
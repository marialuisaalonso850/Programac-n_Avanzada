package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class CerrarSolicitudService {

    public void ejecutar(
            Solicitud solicitud,
            Usuario usuarioActual,
            String observacion
    ) {

        if (!usuarioActual.equals(solicitud.getResponsable())
                && !usuarioActual.esAdmin()) {

            throw new ReglaDominioException(
                    "Solo el responsable puede cerrar la solicitud"
            );
        }

        solicitud.cerrar(observacion);
    }
}
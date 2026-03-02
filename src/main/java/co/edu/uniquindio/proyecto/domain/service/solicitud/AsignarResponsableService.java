package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class AsignarResponsableService {

    public void ejecutar(
            Solicitud solicitud,
            Usuario usuarioActual,
            Usuario responsable
    ) {

        if (!usuarioActual.esAdmin()
                && !usuarioActual.esCoordinador()) {

            throw new ReglaDominioException(
                    "Solo un administrador o coordinador puede asignar responsable"
            );
        }

        if (!responsable.puedeSerResponsable()) {
            throw new ReglaDominioException(
                    "El usuario seleccionado no puede ser responsable"
            );
        }

        solicitud.asignarResponsable(responsable);
    }
}
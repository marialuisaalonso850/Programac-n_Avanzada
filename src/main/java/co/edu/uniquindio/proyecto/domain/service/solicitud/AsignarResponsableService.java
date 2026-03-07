package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Servicio de dominio encargado de gestionar la asignación
 * de un responsable a una solicitud.
 *
 * <p>Este servicio valida que el usuario que realiza la acción
 * tenga los permisos necesarios (administrador o coordinador)
 * y que el usuario seleccionado pueda ser responsable.</p>
 */
public class AsignarResponsableService {

    /**
     * Ejecuta la asignación de un responsable a una solicitud.
     *
     * @param solicitud solicitud a la cual se le asignará un responsable
     * @param usuarioActual usuario que realiza la acción
     * @param responsable usuario que será asignado como responsable
     */
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
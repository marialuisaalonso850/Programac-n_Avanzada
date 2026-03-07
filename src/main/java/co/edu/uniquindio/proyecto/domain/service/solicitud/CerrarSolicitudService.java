package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Servicio de dominio encargado de gestionar el cierre de una solicitud.
 *
 * <p>Este servicio valida que el usuario que realiza la acción tenga
 * autorización para cerrar la solicitud. Solo el responsable asignado
 * o un administrador pueden ejecutar esta operación.</p>
 */
public class CerrarSolicitudService {

    /**
     * Ejecuta el cierre de una solicitud.
     *
     * @param solicitud solicitud que se desea cerrar
     * @param usuarioActual usuario que intenta realizar el cierre
     * @param observacion comentario o justificación del cierre
     */
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
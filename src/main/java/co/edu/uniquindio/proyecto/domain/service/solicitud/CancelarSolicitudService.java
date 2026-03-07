package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Servicio de dominio encargado de gestionar la cancelación
 * de una solicitud.
 *
 * <p>Este servicio valida que el usuario que realiza la acción
 * tenga autorización para cancelar la solicitud, ya sea porque
 * es el propietario de la misma o porque posee rol de administrador.</p>
 */
public class CancelarSolicitudService {

    /**
     * Ejecuta la cancelación de una solicitud.
     *
     * @param solicitud solicitud que se desea cancelar
     * @param usuarioActual usuario que intenta realizar la cancelación
     * @param observacion motivo o comentario asociado a la cancelación
     */
    public void ejecutar(
            Solicitud solicitud,
            Usuario usuarioActual,
            String observacion
    ) {

        if (!usuarioActual.equals(solicitud.getUsuario())
                && !usuarioActual.esAdmin()) {

            throw new ReglaDominioException("No autorizado para cancelar");
        }

        solicitud.cancelar(observacion);
    }
}
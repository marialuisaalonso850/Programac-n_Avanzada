package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Servicio de dominio encargado de gestionar el proceso de marcar
 * una solicitud como atendida.
 *
 * <p>Este servicio valida que el usuario que realiza la acción
 * tenga autorización para hacerlo. Solo el responsable asignado
 * a la solicitud o un administrador pueden ejecutar esta operación.</p>
 */
public class MarcarComoAtendidaService {

    /**
     * Ejecuta la acción de marcar una solicitud como atendida.
     *
     * @param solicitud solicitud que se desea marcar como atendida
     * @param usuarioActual usuario que intenta realizar la acción
     * @param observacion comentario o detalle sobre la atención realizada
     */
    public void ejecutar(
            Solicitud solicitud,
            Usuario usuarioActual,
            String observacion
    ) {

        if (!usuarioActual.equals(solicitud.getResponsable())
                && !usuarioActual.esAdmin()) {

            throw new ReglaDominioException(
                    "Solo el responsable o admin puede marcar como atendida"
            );
        }

        solicitud.marcarComoAtendida(observacion);
    }
}
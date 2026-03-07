package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Servicio de dominio encargado de gestionar la clasificación
 * de una solicitud.
 *
 * <p>Este servicio valida que el usuario que realiza la acción
 * tenga permisos suficientes para clasificar la solicitud,
 * permitiendo la operación únicamente a administradores
 * o coordinadores.</p>
 */
public class ClasificarSolicitudService {

    /**
     * Ejecuta la clasificación de una solicitud asignándole un tipo.
     *
     * @param solicitud solicitud que será clasificada
     * @param nuevoTipo tipo de solicitud que se desea asignar
     * @param usuarioActual usuario que realiza la clasificación
     * @param observacion comentario asociado a la clasificación
     */
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
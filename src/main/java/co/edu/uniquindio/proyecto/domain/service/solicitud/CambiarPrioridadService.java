package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Servicio de dominio encargado de gestionar el cambio de prioridad
 * de una solicitud.
 *
 * <p>Este servicio valida que el usuario que realiza la acción tenga
 * permisos suficientes para modificar la prioridad de la solicitud,
 * de acuerdo con las reglas del dominio.</p>
 */
public class CambiarPrioridadService {

    /**
     * Ejecuta el cambio de prioridad de una solicitud.
     *
     * @param solicitud solicitud cuya prioridad será modificada
     * @param nuevaPrioridad nueva prioridad que se desea asignar
     * @param justificacion motivo del cambio de prioridad
     * @param usuarioActual usuario que realiza la modificación
     */
    public void ejecutar(
            Solicitud solicitud,
            Prioridad nuevaPrioridad,
            String justificacion,
            Usuario usuarioActual
    ) {

        if (!usuarioActual.puedeCambiarPrioridad()) {
            throw new ReglaDominioException(
                    "Solo un administrador o coordinador puede cambiar la prioridad"
            );
        }

        solicitud.cambiarPrioridad(
                nuevaPrioridad,
                justificacion,
                usuarioActual
        );
    }
}
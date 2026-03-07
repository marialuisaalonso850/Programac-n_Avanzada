package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Servicio de dominio encargado de gestionar la desactivación
 * de un usuario del sistema.
 *
 * <p>Este servicio valida que el usuario no tenga solicitudes activas
 * antes de permitir su desactivación.</p>
 */
public class DesactivarUsuarioService {

    /**
     * Ejecuta la desactivación de un usuario.
     *
     * @param usuario usuario que se desea desactivar
     * @param tieneSolicitudesActivas indica si el usuario posee solicitudes activas
     */
    public void ejecutar(Usuario usuario, boolean tieneSolicitudesActivas) {

        if (tieneSolicitudesActivas) {
            throw new ReglaDominioException("No se puede desactivar el usuario");
        }

        usuario.desactivar();
    }
}
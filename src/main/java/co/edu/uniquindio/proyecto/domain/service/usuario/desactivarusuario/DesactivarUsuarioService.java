package co.edu.uniquindio.proyecto.domain.service.usuario.desactivarusuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class DesactivarUsuarioService {

    public void desactivarUsuario(Usuario usuario, boolean tieneSolicitudesActivas) {

        if (tieneSolicitudesActivas) {
            throw new ReglaDominioException(
                    "No se puede desactivar un usuario con solicitudes activas"
            );
        }

        usuario.desactivar();
    }
}

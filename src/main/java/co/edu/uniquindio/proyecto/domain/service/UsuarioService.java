package co.edu.uniquindio.proyecto.domain.service;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class UsuarioService {

    public void desactivarUsuario(Usuario usuario, boolean tieneSolicitudesActivas) {

        if (tieneSolicitudesActivas) {
            throw new ReglaDominioException(
                    "No se puede desactivar un usuario con solicitudes activas"
            );
        }

        usuario.desactivar();
    }
}
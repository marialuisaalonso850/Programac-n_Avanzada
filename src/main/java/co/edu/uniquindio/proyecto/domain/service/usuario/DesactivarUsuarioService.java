package co.edu.uniquindio.proyecto.domain.service.usuario;


import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class DesactivarUsuarioService {

    public void ejecutar(Usuario usuario, boolean tieneSolicitudesActivas) {

        if (tieneSolicitudesActivas) {
            throw new ReglaDominioException("No se puede desactivar el usuario");
        }

        usuario.desactivar();
    }
}
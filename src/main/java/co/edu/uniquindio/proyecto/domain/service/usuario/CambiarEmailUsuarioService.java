package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;

public class CambiarEmailUsuarioService {

    public void ejecutar(Usuario usuario, Email nuevoEmail) {
        usuario.cambiarEmail(nuevoEmail);
    }
}
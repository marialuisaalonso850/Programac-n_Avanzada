package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;

public class CambiarNombreUsuarioService {

    public void ejecutar(Usuario usuario, String nuevoNombre) {
        usuario.cambiarNombre(nuevoNombre);
    }
}
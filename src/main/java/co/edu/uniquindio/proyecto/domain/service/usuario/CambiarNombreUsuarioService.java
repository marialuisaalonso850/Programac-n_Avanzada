package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;

/**
 * Servicio de dominio encargado de gestionar el cambio
 * de nombre de un usuario.
 *
 * <p>Este servicio delega la validación de las reglas
 * de negocio a la entidad {@link Usuario}.</p>
 */
public class CambiarNombreUsuarioService {

    /**
     * Ejecuta el cambio de nombre de un usuario.
     *
     * @param usuario usuario al que se le modificará el nombre
     * @param nuevoNombre nuevo nombre que será asignado al usuario
     */
    public void ejecutar(Usuario usuario, String nuevoNombre) {
        usuario.cambiarNombre(nuevoNombre);
    }
}
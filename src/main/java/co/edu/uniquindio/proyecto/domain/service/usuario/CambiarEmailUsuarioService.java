package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;

/**
 * Servicio de dominio encargado de gestionar el cambio
 * de correo electrónico de un usuario.
 *
 * <p>Este servicio delega la validación de las reglas
 * de negocio a la entidad {@link Usuario}.</p>
 */
public class CambiarEmailUsuarioService {

    /**
     * Ejecuta el cambio de correo electrónico de un usuario.
     *
     * @param usuario usuario al que se le modificará el correo electrónico
     * @param nuevoEmail nuevo correo electrónico que será asignado
     */
    public void ejecutar(Usuario usuario, Email nuevoEmail) {
        usuario.cambiarEmail(nuevoEmail);
    }
}
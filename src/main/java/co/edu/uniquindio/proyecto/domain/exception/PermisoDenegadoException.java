package co.edu.uniquindio.proyecto.domain.exception;

/**
 * Excepción que se lanza cuando un usuario intenta realizar
 * una acción para la cual no tiene permisos dentro del sistema.
 *
 * <p>Se utiliza para indicar violaciones de seguridad o de
 * autorización en las operaciones del dominio.</p>
 */
public class PermisoDenegadoException extends RuntimeException {

    /**
     * Crea una nueva excepción de permiso denegado con un mensaje descriptivo.
     *
     * @param mensaje descripción del error ocurrido
     */
    public PermisoDenegadoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea una nueva excepción de permiso denegado con un mensaje
     * y la causa original del error.
     *
     * @param mensaje descripción del error ocurrido
     * @param causa excepción que originó el problema
     */
    public PermisoDenegadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
package co.edu.uniquindio.proyecto.domain.exception;

/**
 * Excepción que se lanza cuando se intenta realizar una operación
 * que no es válida para el estado actual de una entidad del dominio.
 *
 * <p>Por ejemplo, cuando se intenta cambiar el estado de una solicitud
 * a uno que no está permitido según las reglas de negocio.</p>
 */
public class EstadoInvalidoException extends ReglaDominioException {

    /**
     * Crea una nueva excepción de estado inválido con el mensaje indicado.
     *
     * @param mensaje descripción del error ocurrido
     */
    public EstadoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
package co.edu.uniquindio.proyecto.domain.exception;

/**
 * Excepción que se lanza cuando se intenta realizar una operación
 * sobre una solicitud que ya se encuentra cerrada.
 *
 * <p>Una solicitud cerrada no puede ser modificada ni cambiar
 * su estado según las reglas del dominio.</p>
 */
public class SolicitudCerradaException extends ReglaDominioException {

    /**
     * Crea una nueva excepción indicando que la solicitud ya está cerrada.
     *
     * @param mensaje descripción del error ocurrido
     */
    public SolicitudCerradaException(String mensaje) {
        super(mensaje);
    }
}
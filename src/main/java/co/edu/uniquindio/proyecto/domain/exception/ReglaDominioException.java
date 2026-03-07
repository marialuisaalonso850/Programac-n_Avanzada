package co.edu.uniquindio.proyecto.domain.exception;

/**
 * Excepción base para representar violaciones de reglas de negocio
 * dentro del dominio del sistema.
 *
 * <p>Se utiliza cuando una operación no puede ejecutarse porque
 * infringe alguna regla del modelo de dominio.</p>
 */
public class ReglaDominioException extends RuntimeException {

    /**
     * Crea una nueva excepción de regla de dominio con el mensaje indicado.
     *
     * @param mensaje descripción del error ocurrido
     */
    public ReglaDominioException(String mensaje) {
        super(mensaje);
    }
}
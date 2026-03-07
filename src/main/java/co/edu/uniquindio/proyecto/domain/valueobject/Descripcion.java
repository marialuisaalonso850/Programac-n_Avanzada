package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Value Object que representa la descripción de una entidad del dominio.
 *
 * <p>Garantiza las siguientes reglas de negocio:</p>
 * <ul>
 *     <li>No puede ser nula.</li>
 *     <li>No puede estar vacía.</li>
 *     <li>Debe tener entre 10 y 500 caracteres.</li>
 * </ul>
 *
 * <p>Es inmutable, ya que está implementado como un {@code record}.</p>
 *
 * @param valor texto de la descripción validado según las reglas del dominio.
 * @throws ReglaDominioException si no cumple las validaciones establecidas.
 */
public record Descripcion(String valor) {

    private static final int LONGITUD_MINIMA = 10;
    private static final int LONGITUD_MAXIMA = 500;

    /**
     * Constructor compacto que valida las reglas de negocio.
     */
    public Descripcion {
        if (valor == null || valor.isBlank()) {
            throw new ReglaDominioException("La descripción es obligatoria");
        }

        valor = valor.trim();

        if (valor.length() < LONGITUD_MINIMA) {
            throw new ReglaDominioException(
                    "La descripción debe tener al menos " + LONGITUD_MINIMA + " caracteres"
            );
        }

        if (valor.length() > LONGITUD_MAXIMA) {
            throw new ReglaDominioException(
                    "La descripción no puede superar " + LONGITUD_MAXIMA + " caracteres"
            );
        }
    }
}
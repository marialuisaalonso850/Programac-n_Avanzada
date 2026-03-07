package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

/**
 * Value Object que representa un documento de identidad.
 *
 * <p>Está compuesto por:</p>
 * <ul>
 *     <li>Tipo de documento ({@link TipoDocumento})</li>
 *     <li>Número de documento</li>
 * </ul>
 *
 * <p>Garantiza que:</p>
 * <ul>
 *     <li>El tipo no sea nulo.</li>
 *     <li>El número no sea nulo ni vacío.</li>
 *     <li>El número no contenga espacios innecesarios.</li>
 * </ul>
 *
 * @param tipo   tipo de documento.
 * @param numero número de documento.
 * @throws ReglaDominioException si alguna regla de negocio no se cumple.
 */
public record DocumentoIdentidad(
        TipoDocumento tipo,
        String numero
) {

    public DocumentoIdentidad {

        if (tipo == null) {
            throw new ReglaDominioException("El tipo de documento es obligatorio");
        }

        if (numero == null || numero.isBlank()) {
            throw new ReglaDominioException("El número de documento es obligatorio");
        }

        numero = numero.trim();

        if (numero.length() < 5) {
            throw new ReglaDominioException("El número de documento es demasiado corto");
        }
    }
}
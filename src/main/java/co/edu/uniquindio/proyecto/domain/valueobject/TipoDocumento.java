package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa los tipos de documentos de identidad permitidos
 * dentro del sistema.
 *
 * <p>Se utiliza en el Value Object {@code DocumentoIdentidad}
 * para clasificar el tipo de identificación del usuario.</p>
 *
 * <ul>
 *     <li>CEDULA_CIUDADANIA: Documento nacional de identificación.</li>
 *     <li>TARJETA_IDENTIDAD: Documento para menores de edad.</li>
 *     <li>CEDULA_EXTRANJERIA: Documento para extranjeros residentes.</li>
 *     <li>PASAPORTE: Documento internacional de identificación.</li>
 * </ul>
 */
public enum TipoDocumento {

    CEDULA_CIUDADANIA,
    TARJETA_IDENTIDAD,
    CEDULA_EXTRANJERIA,
    PASAPORTE
}
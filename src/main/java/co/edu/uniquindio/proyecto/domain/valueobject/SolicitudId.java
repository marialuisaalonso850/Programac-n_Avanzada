package co.edu.uniquindio.proyecto.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object que representa el identificador único de una solicitud.
 *
 * <p>Encapsula un {@link UUID} y garantiza que no sea nulo.
 * Proporciona un método para generar un nuevo identificador de forma segura.</p>
 *
 * <p>Es inmutable y su igualdad se basa en el valor del UUID.</p>
 */
public record SolicitudId(UUID value) {

    /**
     * Constructor compacto que valida que el valor no sea nulo.
     *
     * @param value UUID de la solicitud
     * @throws IllegalArgumentException si el valor es {@code null}
     */
    public SolicitudId {
        if (value == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
    }

    /**
     * Genera un nuevo {@link SolicitudId} con un {@link UUID} aleatorio.
     *
     * @return nuevo identificador único de solicitud
     */
    public static SolicitudId generar() {
        return new SolicitudId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
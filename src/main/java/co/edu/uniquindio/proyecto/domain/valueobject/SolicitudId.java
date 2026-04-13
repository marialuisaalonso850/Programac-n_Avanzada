package co.edu.uniquindio.proyecto.domain.valueobject;

import java.util.UUID;

public record SolicitudId(UUID value) {

    public SolicitudId {
        if (value == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
    }

    public static SolicitudId generar() {
        return new SolicitudId(UUID.randomUUID());
    }

    public String getValue() {
        return value.toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
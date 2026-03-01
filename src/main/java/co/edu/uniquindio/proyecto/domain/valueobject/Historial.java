package co.edu.uniquindio.proyecto.domain.valueobject;

import java.time.LocalDateTime;

public record Historial(
        LocalDateTime fechaHora,
        TipoAccion accion,
        DocumentoIdentidad usuarioId,
        String observacion
) {

    public Historial {

        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora no puede ser null");
        }

        if (accion == null) {
            throw new IllegalArgumentException("La acción es obligatoria");
        }

        if (usuarioId == null ) {
            throw new IllegalArgumentException("El usuario responsable es obligatorio");
        }
    }
}
package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public record Descripcion(String valor) {

    private static final int LONGITUD_MINIMA = 10;
    private static final int LONGITUD_MAXIMA = 500;

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
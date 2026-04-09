package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

import java.util.regex.Pattern;

public record Email(String valor) {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public Email {
        if (valor == null || valor.isBlank()) {
            throw new ReglaDominioException("El email es obligatorio");
        }

        if (!Pattern.matches(EMAIL_REGEX, valor)) {
            throw new ReglaDominioException("El formato del email es inválido");
        }
    }
}
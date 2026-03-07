package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object que representa un correo electrónico.
 *
 * <p>Garantiza las siguientes reglas de negocio:</p>
 * <ul>
 *     <li>No puede ser nulo.</li>
 *     <li>No puede estar vacío.</li>
 *     <li>Debe cumplir con un formato válido de correo electrónico.</li>
 * </ul>
 *
 * <p>Es inmutable y define igualdad basada en su valor.</p>
 */
public class Email {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private final String valor;

    /**
     * Construye un Email validando sus reglas de dominio.
     *
     * @param valor correo electrónico.
     * @throws ReglaDominioException si no cumple las validaciones.
     */
    public Email(String valor) {

        if (valor == null || valor.isBlank()) {
            throw new ReglaDominioException("El email es obligatorio");
        }

        if (!Pattern.matches(EMAIL_REGEX, valor)) {
            throw new ReglaDominioException("El formato del email es inválido");
        }

        this.valor = valor;
    }

    /** Retorna el valor del correo electrónico. */
    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return valor.equals(email.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
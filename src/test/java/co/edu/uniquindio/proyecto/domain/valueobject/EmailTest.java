package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el value object {@link Email}.
 *
 * <p>Verifica las reglas de validación para la creación
 * de direcciones de correo electrónico.</p>
 */
class EmailTest {

    /**
     * Verifica que se lance una excepción cuando el email
     * tiene un formato inválido.
     */
    @Test
    void noDebeCrearEmailInvalido() {
        assertThrows(ReglaDominioException.class,
                () -> new Email("correo-invalido"));
    }

    /**
     * Verifica que se lance una excepción cuando el email está vacío.
     */
    @Test
    void noDebeCrearEmailVacio() {
        assertThrows(ReglaDominioException.class,
                () -> new Email(""));
    }

    /**
     * Verifica que un email válido se cree correctamente.
     */
    @Test
    void debeCrearEmailValido() {
        Email email = new Email("test@correo.com");

        assertEquals("test@correo.com", email.getValor());
    }
}
package co.edu.uniquindio.proyecto.domain.model;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void noDebeCrearEmailInvalido() {
        assertThrows(ReglaDominioException.class,
                () -> new Email("correo-invalido"));
    }

    @Test
    void noDebeCrearEmailVacio() {
        assertThrows(ReglaDominioException.class,
                () -> new Email(""));
    }

    @Test
    void debeCrearEmailValido() {
        Email email = new Email("test@correo.com");

        assertEquals("test@correo.com", email.getValor());
    }
}
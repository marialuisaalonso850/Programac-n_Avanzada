package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el value object {@link Descripcion}.
 *
 * <p>Verifica las reglas de validación aplicadas al crear una descripción.</p>
 */
class DescripcionTest {

    /**
     * Verifica que una descripción válida se cree correctamente.
     */
    @Test
    void descripcion_valida_seCreaCorrectamente() {

        Descripcion descripcion = new Descripcion("Descripcion valida");

        assertEquals("Descripcion valida", descripcion.valor());
    }

    /**
     * Verifica que se lance una excepción cuando la descripción es null.
     */
    @Test
    void descripcion_null_lanzaExcepcion() {

        assertThrows(ReglaDominioException.class, () ->
                new Descripcion(null)
        );
    }

    /**
     * Verifica que se lance una excepción cuando la descripción está vacía.
     */
    @Test
    void descripcion_vacia_lanzaExcepcion() {

        assertThrows(ReglaDominioException.class, () ->
                new Descripcion("   ")
        );
    }

    /**
     * Verifica que se lance una excepción cuando la descripción
     * es menor a la longitud mínima permitida.
     */
    @Test
    void descripcion_menorALongitudMinima_lanzaExcepcion() {

        assertThrows(ReglaDominioException.class, () ->
                new Descripcion("corta")
        );
    }

    /**
     * Verifica que se lance una excepción cuando la descripción
     * supera la longitud máxima permitida.
     */
    @Test
    void descripcion_superaLongitudMaxima_lanzaExcepcion() {

        String textoLargo = "a".repeat(501);

        assertThrows(ReglaDominioException.class, () ->
                new Descripcion(textoLargo)
        );
    }

    /**
     * Verifica que los espacios al inicio y al final
     * de la descripción sean eliminados correctamente.
     */
    @Test
    void descripcion_eliminaEspaciosExtremos() {

        Descripcion descripcion = new Descripcion("   descripcion valida   ");

        assertEquals("descripcion valida", descripcion.valor());
    }
}
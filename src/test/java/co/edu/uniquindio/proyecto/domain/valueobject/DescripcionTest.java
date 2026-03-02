package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescripcionTest {

    @Test
    void descripcion_valida_seCreaCorrectamente() {

        Descripcion descripcion = new Descripcion("Descripcion valida");

        assertEquals("Descripcion valida", descripcion.valor());
    }

    @Test
    void descripcion_null_lanzaExcepcion() {

        assertThrows(ReglaDominioException.class, () ->
                new Descripcion(null)
        );
    }
    @Test
    void descripcion_vacia_lanzaExcepcion() {

        assertThrows(ReglaDominioException.class, () ->
                new Descripcion("   ")
        );
    }

    @Test
    void descripcion_menorALongitudMinima_lanzaExcepcion() {

        assertThrows(ReglaDominioException.class, () ->
                new Descripcion("corta")
        );
    }
    @Test
    void descripcion_superaLongitudMaxima_lanzaExcepcion() {

        String textoLargo = "a".repeat(501);

        assertThrows(ReglaDominioException.class, () ->
                new Descripcion(textoLargo)
        );
    }

    @Test
    void descripcion_eliminaEspaciosExtremos() {

        Descripcion descripcion = new Descripcion("   descripcion valida   ");

        assertEquals("descripcion valida", descripcion.valor());
    }
}
package co.edu.uniquindio.proyecto.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Pruebas unitarias para el value object {@link Historial}.
 *
 * <p>Verifica la correcta creación de registros de historial
 * y la validación de los datos obligatorios.</p>
 */
class HistorialTest {

    /**
     * Verifica que un historial válido se cree correctamente
     * con todos sus atributos.
     */
    @Test
    void historial_valido_seCreaCorrectamente() {

        LocalDateTime fecha = LocalDateTime.now();
        TipoAccion accion = mock(TipoAccion.class);
        DocumentoIdentidad usuario = mock(DocumentoIdentidad.class);

        Historial historial = new Historial(
                fecha,
                accion,
                usuario,
                "observación"
        );

        assertEquals(fecha, historial.fechaHora());
        assertEquals(accion, historial.accion());
        assertEquals(usuario, historial.usuarioId());
        assertEquals("observación", historial.observacion());
    }

    /**
     * Verifica que se lance una excepción cuando la fecha es null.
     */
    @Test
    void fechaNull_lanzaExcepcion() {

        TipoAccion accion = mock(TipoAccion.class);
        DocumentoIdentidad usuario = mock(DocumentoIdentidad.class);

        assertThrows(IllegalArgumentException.class, () ->
                new Historial(
                        null,
                        accion,
                        usuario,
                        "obs"
                )
        );
    }

    /**
     * Verifica que se lance una excepción cuando la acción es null.
     */
    @Test
    void accionNull_lanzaExcepcion() {

        DocumentoIdentidad usuario = mock(DocumentoIdentidad.class);

        assertThrows(IllegalArgumentException.class, () ->
                new Historial(
                        LocalDateTime.now(),
                        null,
                        usuario,
                        "obs"
                )
        );
    }

    /**
     * Verifica que se lance una excepción cuando el usuario es null.
     */
    @Test
    void usuarioNull_lanzaExcepcion() {

        TipoAccion accion = mock(TipoAccion.class);

        assertThrows(IllegalArgumentException.class, () ->
                new Historial(
                        LocalDateTime.now(),
                        accion,
                        null,
                        "obs"
                )
        );
    }
}
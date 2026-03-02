package co.edu.uniquindio.proyecto.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HistorialTest {

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
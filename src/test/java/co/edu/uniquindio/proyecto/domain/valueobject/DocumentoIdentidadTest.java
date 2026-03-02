package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DocumentoIdentidadTest {

    @Test
    void documento_valido_seCreaCorrectamente() {

        TipoDocumento tipo = mock(TipoDocumento.class);

        DocumentoIdentidad documento =
                new DocumentoIdentidad(tipo, "123456");

        assertEquals(tipo, documento.tipo());
        assertEquals("123456", documento.numero());
    }

    @Test
    void tipo_null_lanzaExcepcion() {

        assertThrows(ReglaDominioException.class, () ->
                new DocumentoIdentidad(null, "123456")
        );
    }

    @Test
    void numero_null_lanzaExcepcion() {

        TipoDocumento tipo = mock(TipoDocumento.class);

        assertThrows(ReglaDominioException.class, () ->
                new DocumentoIdentidad(tipo, null)
        );
    }
    @Test
    void numero_vacio_lanzaExcepcion() {

        TipoDocumento tipo = mock(TipoDocumento.class);

        assertThrows(ReglaDominioException.class, () ->
                new DocumentoIdentidad(tipo, "   ")
        );
    }
}
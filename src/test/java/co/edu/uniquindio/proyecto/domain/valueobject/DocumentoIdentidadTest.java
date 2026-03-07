package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Pruebas unitarias para el value object {@link DocumentoIdentidad}.
 *
 * <p>Verifica las reglas de validación aplicadas al crear
 * un documento de identidad.</p>
 */
class DocumentoIdentidadTest {

    /**
     * Verifica que un documento de identidad válido
     * se cree correctamente.
     */
    @Test
    void documento_valido_seCreaCorrectamente() {

        TipoDocumento tipo = mock(TipoDocumento.class);

        DocumentoIdentidad documento =
                new DocumentoIdentidad(tipo, "123456");

        assertEquals(tipo, documento.tipo());
        assertEquals("123456", documento.numero());
    }

    /**
     * Verifica que se lance una excepción cuando el tipo
     * de documento es null.
     */
    @Test
    void tipo_null_lanzaExcepcion() {

        assertThrows(ReglaDominioException.class, () ->
                new DocumentoIdentidad(null, "123456")
        );
    }

    /**
     * Verifica que se lance una excepción cuando
     * el número del documento es null.
     */
    @Test
    void numero_null_lanzaExcepcion() {

        TipoDocumento tipo = mock(TipoDocumento.class);

        assertThrows(ReglaDominioException.class, () ->
                new DocumentoIdentidad(tipo, null)
        );
    }

    /**
     * Verifica que se lance una excepción cuando
     * el número del documento está vacío.
     */
    @Test
    void numero_vacio_lanzaExcepcion() {

        TipoDocumento tipo = mock(TipoDocumento.class);

        assertThrows(ReglaDominioException.class, () ->
                new DocumentoIdentidad(tipo, "   ")
        );
    }
}
package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el servicio {@link DesactivarUsuarioService}.
 *
 * <p>Verifica las reglas de negocio relacionadas con la desactivación
 * de un usuario dentro del sistema.</p>
 */
class DesactivarUsuarioServiceTest {

    private DesactivarUsuarioService service;
    private Usuario usuario;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        service = new DesactivarUsuarioService();
        usuario = mock(Usuario.class);
    }

    /**
     * Verifica que el usuario se desactive correctamente
     * cuando no tiene solicitudes activas.
     */
    @Test
    void deberiaDesactivarSiNoTieneSolicitudesActivas() {

        service.ejecutar(usuario, false);

        verify(usuario).desactivar();
    }

    /**
     * Verifica que se lance una excepción si el usuario
     * tiene solicitudes activas y no puede desactivarse.
     */
    @Test
    void deberiaLanzarExcepcionSiTieneSolicitudesActivas() {

        assertThrows(ReglaDominioException.class,
                () -> service.ejecutar(usuario, true));

        verify(usuario, never()).desactivar();
    }
}
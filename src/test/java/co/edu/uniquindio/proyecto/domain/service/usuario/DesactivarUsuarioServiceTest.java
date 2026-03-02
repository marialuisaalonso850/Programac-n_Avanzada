package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DesactivarUsuarioServiceTest {

    private DesactivarUsuarioService service;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        service = new DesactivarUsuarioService();
        usuario = mock(Usuario.class);
    }

    @Test
    void deberiaDesactivarSiNoTieneSolicitudesActivas() {

        service.ejecutar(usuario, false);

        verify(usuario).desactivar();
    }

    @Test
    void deberiaLanzarExcepcionSiTieneSolicitudesActivas() {

        assertThrows(ReglaDominioException.class,
                () -> service.ejecutar(usuario, true));

        verify(usuario, never()).desactivar();
    }
}
package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CambiarNombreUsuarioServiceTest {

    private CambiarNombreUsuarioService service;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        service = new CambiarNombreUsuarioService();
        usuario = mock(Usuario.class);
    }

    @Test
    void deberiaCambiarNombre() {

        service.ejecutar(usuario, "Nuevo Nombre");

        verify(usuario).cambiarNombre("Nuevo Nombre");
    }
}
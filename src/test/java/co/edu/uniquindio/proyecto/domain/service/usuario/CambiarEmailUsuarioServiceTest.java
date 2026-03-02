package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CambiarEmailUsuarioServiceTest {

    private CambiarEmailUsuarioService service;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        service = new CambiarEmailUsuarioService();
        usuario = mock(Usuario.class);
    }

    @Test
    void deberiaCambiarEmail() {

        Email email = mock(Email.class);

        service.ejecutar(usuario, email);

        verify(usuario).cambiarEmail(email);
    }
}
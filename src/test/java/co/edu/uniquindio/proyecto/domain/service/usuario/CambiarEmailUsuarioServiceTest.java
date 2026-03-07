package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Pruebas unitarias para el servicio {@link CambiarEmailUsuarioService}.
 *
 * <p>Verifica que el servicio delegue correctamente el cambio de email
 * al usuario correspondiente.</p>
 */
class CambiarEmailUsuarioServiceTest {

    private CambiarEmailUsuarioService service;
    private Usuario usuario;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        service = new CambiarEmailUsuarioService();
        usuario = mock(Usuario.class);
    }

    /**
     * Verifica que el servicio invoque correctamente el método
     * para cambiar el email del usuario.
     */
    @Test
    void deberiaCambiarEmail() {

        Email email = mock(Email.class);

        service.ejecutar(usuario, email);

        verify(usuario).cambiarEmail(email);
    }
}
package co.edu.uniquindio.proyecto.domain.service.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Pruebas unitarias para el servicio {@link CambiarNombreUsuarioService}.
 *
 * <p>Verifica que el servicio delegue correctamente el cambio de nombre
 * al usuario correspondiente.</p>
 */
class CambiarNombreUsuarioServiceTest {

    private CambiarNombreUsuarioService service;
    private Usuario usuario;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        service = new CambiarNombreUsuarioService();
        usuario = mock(Usuario.class);
    }

    /**
     * Verifica que el servicio invoque correctamente el método
     * para cambiar el nombre del usuario.
     */
    @Test
    void deberiaCambiarNombre() {

        service.ejecutar(usuario, "Nuevo Nombre");

        verify(usuario).cambiarNombre("Nuevo Nombre");
    }
}
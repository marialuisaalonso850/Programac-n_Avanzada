package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.TestData.UsuarioTestDataFactory;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = UsuarioTestDataFactory.crearUsuarioValido();
    }

    @Test
    void debeCrearseActivoPorDefecto() {

        assertEquals(EstadoUsuario.ACTIVO, usuario.getEstado());
        assertNotNull(usuario.getIdentificacion());
    }

    @Test
    void noDebeCrearUsuarioConNombreVacio() {

        assertThrows(ReglaDominioException.class, () ->
                new Usuario(
                        new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "999"),
                        "",
                        new Email("test@test.com"),
                        TipoUsuario.ESTUDIANTE
                )
        );
    }

    @Test
    void noDebeCrearUsuarioConEmailNulo() {

        assertThrows(ReglaDominioException.class, () ->
                new Usuario(
                        new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "999"),
                        "Nombre",
                        null,
                        TipoUsuario.ESTUDIANTE
                )
        );
    }

    @Test
    void debeDesactivarUsuario() {

        usuario.desactivar();

        assertEquals(EstadoUsuario.INACTIVO, usuario.getEstado());
    }

    @Test
    void noDebeDesactivarSiYaEstaInactivo() {

        usuario.desactivar();

        assertThrows(ReglaDominioException.class,
                () -> usuario.desactivar());
    }

    @Test
    void debeActivarUsuario() {

        usuario.desactivar();
        usuario.activar();

        assertEquals(EstadoUsuario.ACTIVO, usuario.getEstado());
    }

    @Test
    void noDebeActivarSiYaEstaActivo() {

        assertThrows(ReglaDominioException.class,
                () -> usuario.activar());
    }
    @Test
    void debeCambiarEmailSiEstaActivo() {

        Email nuevo = new Email("nuevo@test.com");

        usuario.cambiarEmail(nuevo);

        assertEquals(nuevo, usuario.getEmail());
    }

    @Test
    void noDebeCambiarEmailSiEstaInactivo() {

        usuario.desactivar();

        assertThrows(ReglaDominioException.class,
                () -> usuario.cambiarEmail(new Email("x@test.com")));
    }
    @Test
    void debeCambiarNombreSiEstaActivo() {

        usuario.cambiarNombre("Nuevo Nombre");

        assertEquals("Nuevo Nombre", usuario.getNombre());
    }

    @Test
    void noDebeCambiarNombreSiEstaInactivo() {

        usuario.desactivar();

        assertThrows(ReglaDominioException.class,
                () -> usuario.cambiarNombre("Nombre"));
    }
    @Test
    void docenteDebePoderSerResponsable() {

        Usuario docente = UsuarioTestDataFactory.crearDocenteValido();

        assertTrue(docente.puedeSerResponsable());
    }

    @Test
    void estudianteNoDebeSerResponsable() {

        assertFalse(usuario.puedeSerResponsable());
    }

    @Test
    void adminPuedeCambiarPrioridad() {

        Usuario admin = UsuarioTestDataFactory.crearAdminValido();

        assertTrue(admin.puedeCambiarPrioridad());
    }
}

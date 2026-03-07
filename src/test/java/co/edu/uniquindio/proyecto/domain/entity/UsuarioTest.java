package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.TestData.UsuarioTestDataFactory;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad Usuario.
 * Verifica reglas de negocio relacionadas con el estado,
 * modificación de datos y permisos según el tipo de usuario.
 *
 * @author Maria Luisa Alonso
 * @author Valentina Orlas Pachon
 * @version 1.0
 */
class UsuarioTest {

    private Usuario usuario;
    private Usuario coordinador;
    private Usuario docente;
    private Usuario admin;

    /**
     * Inicializa los usuarios necesarios para las pruebas.
     */
    @BeforeEach
    void setUp() {
        usuario     = UsuarioTestDataFactory.crearUsuarioValido();
        coordinador = UsuarioTestDataFactory.crearCoordinadorValido();
        docente     = UsuarioTestDataFactory.crearDocenteValido();
        admin       = UsuarioTestDataFactory.crearAdminValido();
    }

    /**
     * Verifica que un usuario válido se cree correctamente.
     */
    @Test
    void deberiaCrearUsuarioCorrectamente() {
        assertNotNull(usuario);
        assertEquals(EstadoUsuario.ACTIVO, usuario.getEstado());
    }

    /**
     * No se debe permitir crear un usuario con identificación nula.
     */
    @Test
    void noDebeCrearUsuarioConIdentificacionNula() {
        assertThrows(ReglaDominioException.class, () ->
                new Usuario(null, "Nombre",
                        new Email("test@test.com"), TipoUsuario.ESTUDIANTE));
    }

    /**
     * No se debe permitir crear un usuario con nombre nulo.
     */
    @Test
    void noDebeCrearUsuarioConNombreNulo() {
        assertThrows(ReglaDominioException.class, () ->
                new Usuario(
                        new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "123"),
                        null,
                        new Email("test@test.com"),
                        TipoUsuario.ESTUDIANTE));
    }

    /**
     * No se debe permitir crear un usuario con email nulo.
     */
    @Test
    void noDebeCrearUsuarioConEmailNulo() {
        assertThrows(ReglaDominioException.class, () ->
                new Usuario(
                        new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "123"),
                        "Nombre",
                        null,
                        TipoUsuario.ESTUDIANTE));
    }

    /**
     * No se debe permitir crear un usuario con tipo nulo.
     */
    @Test
    void noDebeCrearUsuarioConTipoNulo() {
        assertThrows(ReglaDominioException.class, () ->
                new Usuario(
                        new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "123"),
                        "Nombre",
                        new Email("test@test.com"),
                        null));
    }

    /**
     * Verifica que un usuario activo pueda ser desactivado.
     */
    @Test
    void deberiaDesactivarUsuarioActivo() {
        usuario.desactivar();
        assertEquals(EstadoUsuario.INACTIVO, usuario.getEstado());
    }

    /**
     * No se debe permitir desactivar un usuario que ya está inactivo.
     */
    @Test
    void noDebeDesactivarUsuarioYaInactivo() {
        usuario.desactivar();

        assertThrows(ReglaDominioException.class, () ->
                usuario.desactivar());
    }

    /**
     * Verifica que un usuario inactivo pueda activarse nuevamente.
     */
    @Test
    void deberiaActivarUsuarioInactivo() {
        usuario.desactivar();
        usuario.activar();

        assertEquals(EstadoUsuario.ACTIVO, usuario.getEstado());
    }

    /**
     * No se debe permitir activar un usuario que ya está activo.
     */
    @Test
    void noDebeActivarUsuarioYaActivo() {
        assertThrows(ReglaDominioException.class, () ->
                usuario.activar());
    }

    /**
     * No se debe permitir modificar el email de un usuario inactivo.
     */
    @Test
    void noDebeCambiarEmailDeUsuarioInactivo() {
        usuario.desactivar();

        assertThrows(ReglaDominioException.class, () ->
                usuario.cambiarEmail(new Email("nuevo@test.com")));
    }

    /**
     * No se debe permitir modificar el nombre de un usuario inactivo.
     */
    @Test
    void noDebeCambiarNombreDeUsuarioInactivo() {
        usuario.desactivar();

        assertThrows(ReglaDominioException.class, () ->
                usuario.cambiarNombre("Nuevo Nombre"));
    }

    /**
     * Verifica que un usuario activo pueda cambiar su email.
     */
    @Test
    void deberiaCambiarEmailDeUsuarioActivo() {
        Email nuevoEmail = new Email("nuevo@test.com");

        usuario.cambiarEmail(nuevoEmail);

        assertEquals(nuevoEmail, usuario.getEmail());
    }

    /**
     * Verifica que un usuario activo pueda cambiar su nombre.
     */
    @Test
    void deberiaCambiarNombreDeUsuarioActivo() {
        usuario.cambiarNombre("Nuevo Nombre");

        assertEquals("Nuevo Nombre", usuario.getNombre());
    }

    /**
     * Verifica que un estudiante no pueda ser responsable de una solicitud.
     */
    @Test
    void estudianteNoDebePoderSerResponsable() {
        assertFalse(usuario.puedeSerResponsable());
    }

    /**
     * Verifica que un docente pueda ser responsable de una solicitud.
     */
    @Test
    void docenteDebePoderSerResponsable() {
        assertTrue(docente.puedeSerResponsable());
    }

    /**
     * Verifica que un coordinador pueda ser responsable de una solicitud.
     */
    @Test
    void coordinadorDebePoderSerResponsable() {
        assertTrue(coordinador.puedeSerResponsable());
    }

    /**
     * Verifica que un administrador pueda cambiar la prioridad de una solicitud.
     */
    @Test
    void adminDebePoderCambiarPrioridad() {
        assertTrue(admin.puedeCambiarPrioridad());
    }

    /**
     * Verifica que un coordinador pueda cambiar la prioridad de una solicitud.
     */
    @Test
    void coordinadorDebePoderCambiarPrioridad() {
        assertTrue(coordinador.puedeCambiarPrioridad());
    }

    /**
     * Verifica que un estudiante no pueda cambiar la prioridad.
     */
    @Test
    void estudianteNoDebePoderCambiarPrioridad() {
        assertFalse(usuario.puedeCambiarPrioridad());
    }

    /**
     * Verifica que un usuario inactivo no pueda ser responsable.
     */
    @Test
    void usuarioInactivoNoDebePoderSerResponsable() {
        docente.desactivar();
        assertFalse(docente.puedeSerResponsable());
    }
}
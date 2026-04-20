package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private DocumentoIdentidad idValido;
    private Email emailValido;

    @BeforeEach
    void setUp() {
        idValido = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1004798819");
        emailValido = new Email("test@uniquindio.edu.co");
    }

    @Test
    void crearUsuarioConstructorManualDebeEstarActivo() {
        Usuario usuario = new Usuario(idValido, "Carlos Perez", emailValido, TipoUsuario.DOCENTE, "pass123");

        assertTrue(usuario.estaActivo());
        assertEquals(EstadoUsuario.ACTIVO, usuario.getEstado());
        assertEquals("Carlos Perez", usuario.getNombre());
    }

    @Test
    void debeFallarAlCrearUsuarioConCamposNulos() {
        assertThrows(ReglaDominioException.class, () ->
                new Usuario(null, "Nombre", emailValido, TipoUsuario.ESTUDIANTE, "123")
        );
    }

    @Test
    void validarCambioNombreExitoso() {
        Usuario usuario = new Usuario(idValido, "Luis", emailValido, TipoUsuario.ADMIN, "123");

        usuario.cambiarNombre("Luis Garcia");

        assertEquals("Luis Garcia", usuario.getNombre());
    }

    @Test
    void debeDesactivarUsuarioCorrectamente() {
        Usuario usuario = new Usuario(idValido, "Marta", emailValido, TipoUsuario.COORDINADOR, "123");

        usuario.desactivar();

        assertFalse(usuario.estaActivo());
        assertEquals(EstadoUsuario.INACTIVO, usuario.getEstado());
    }

    @Test
    void debeFallarCambioDeNombreSiUsuarioEstaInactivo() {
        Usuario usuario = new Usuario(idValido, "Marta", emailValido, TipoUsuario.COORDINADOR, "123");
        usuario.desactivar();

        assertThrows(ReglaDominioException.class, () ->
                usuario.cambiarNombre("Nuevo Nombre")
        );
    }

    @Test
    void validarSiPuedeSerResponsable() {
        // Un docente activo puede ser responsable
        Usuario docente = new Usuario(idValido, "Profe", emailValido, TipoUsuario.DOCENTE, "123");
        assertTrue(docente.puedeSerResponsable());

        // Un estudiante no puede ser responsable aunque esté activo
        Usuario estudiante = new Usuario(idValido, "Alumno", emailValido, TipoUsuario.ESTUDIANTE, "123");
        assertFalse(estudiante.puedeSerResponsable());

        // Un coordinador inactivo no puede ser responsable
        Usuario coordinador = new Usuario(idValido, "Coord", emailValido, TipoUsuario.COORDINADOR, "123");
        coordinador.desactivar();
        assertFalse(coordinador.puedeSerResponsable());
    }

    @Test
    void debeValidarTipoDeUsuarioCorrectamente() {
        Usuario admin = new Usuario(idValido, "Admin", emailValido, TipoUsuario.ADMIN, "123");

        assertTrue(admin.esAdmin());
        assertFalse(admin.esDocente());
        assertFalse(admin.esEstudiante());
    }

    @Test
    void cambiarEmailDebeFallarSiEsNulo() {
        Usuario usuario = new Usuario(idValido, "User", emailValido, TipoUsuario.DOCENTE, "123");

        assertThrows(ReglaDominioException.class, () ->
                usuario.cambiarEmail(null)
        );
    }
}
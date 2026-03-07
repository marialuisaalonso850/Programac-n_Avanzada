package co.edu.uniquindio.proyecto.domain.TestData;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;

/**
 * Fábrica de datos de prueba para la entidad {@link Usuario}.
 *
 * <p>Proporciona métodos para crear instancias válidas de usuarios
 * con diferentes roles, facilitando la creación de datos en las pruebas.</p>
 */
public class UsuarioTestDataFactory {

    /**
     * Crea un usuario válido con rol de estudiante.
     *
     * @return usuario estudiante válido
     */
    public static Usuario crearUsuarioValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1001"),
                "Juan Perez",
                new Email("juan@test.com"),
                TipoUsuario.ESTUDIANTE
        );
    }

    /**
     * Crea un usuario válido con rol de coordinador.
     *
     * @return usuario coordinador válido
     */
    public static Usuario crearCoordinadorValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "2002"),
                "Coordinador",
                new Email("coordinador@test.com"),
                TipoUsuario.COORDINADOR
        );
    }

    /**
     * Crea un usuario válido con rol de docente.
     *
     * @return usuario docente válido
     */
    public static Usuario crearDocenteValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "3003"),
                "Docente",
                new Email("docente@test.com"),
                TipoUsuario.DOCENTE
        );
    }

    /**
     * Crea un usuario válido con rol de administrador.
     *
     * @return usuario administrador válido
     */
    public static Usuario crearAdminValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "4004"),
                "Admin",
                new Email("admin@test.com"),
                TipoUsuario.ADMIN
        );
    }
}
package co.edu.uniquindio.proyecto.domain.TestData;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;

public class UsuarioTestDataFactory {

    public static Usuario crearUsuarioValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1001"),
                "Juan Perez",
                new Email("juan@test.com"),
                TipoUsuario.ESTUDIANTE
        );
    }

    public static Usuario crearCoordinadorValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "2002"),
                "Coordinador",
                new Email("coordinador@test.com"),
                TipoUsuario.COORDINADOR
        );
    }

    public static Usuario crearDocenteValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "3003"),
                "Docente",
                new Email("docente@test.com"),
                TipoUsuario.DOCENTE
        );
    }

    public static Usuario crearAdminValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "4004"),
                "Admin",
                new Email("admin@test.com"),
                TipoUsuario.ADMIN
        );
    }

}
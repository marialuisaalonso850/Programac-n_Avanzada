package co.edu.uniquindio.proyecto.domain.TestData;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;

public class UsuarioTestDataFactory {

    public static Usuario crearUsuarioValido() {
        return Usuario.builder() // Si usas @Builder de Lombok
                .identificacion(new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1001"))
                .nombre("Juan Perez")
                .email(new Email("juan@test.com"))
                .tipoUsuario(TipoUsuario.ESTUDIANTE)
                .build();
    }

    public static Usuario crearAdminValido() {
        return new Usuario(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "4004"),
                "Admin",
                new Email("admin@test.com"),
                TipoUsuario.ADMIN,
                "password123" // ¿Tu entidad Usuario pide password? Muchos errores vienen de aquí
        );
    }
}
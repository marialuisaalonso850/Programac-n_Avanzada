package co.edu.uniquindio.proyecto.application.usecase;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.CrearUsuarioUseCase;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CrearUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CrearUsuarioUseCase crearUsuarioUseCase;

    @Test
    void deberiaCrearUsuarioExitosamente() {

        DocumentoIdentidad id = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "12345");
        String nombre = "Juan Perez";
        Email email = new Email("juan@email.com");
        TipoUsuario tipo = TipoUsuario.ESTUDIANTE;


        Usuario resultado = crearUsuarioUseCase.ejecutar(id, nombre, email, tipo);

        assertNotNull(resultado);
        assertEquals(nombre, resultado.getNombre());
        assertEquals(email, resultado.getEmail());

        verify(usuarioRepository).crearUsuario(any(Usuario.class));
    }
}

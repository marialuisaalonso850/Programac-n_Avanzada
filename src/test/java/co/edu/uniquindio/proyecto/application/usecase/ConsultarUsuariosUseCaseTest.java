package co.edu.uniquindio.proyecto.application.usecase;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.ConsultarUsuariosUseCase;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarUsuariosUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ConsultarUsuariosUseCase consultarUsuariosUseCase;

    @Test
    void deberiaRetornarListaVaciaCuandoNoHayUsuarios() {

        when(usuarioRepository.obtenerTodos()).thenReturn(Collections.emptyList());

        var resultado = consultarUsuariosUseCase.ejecutar();

        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).obtenerTodos();
    }
}
package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.application.usecase.usuariocase.ObtenerUsuarioByEmailUseCase;
import co.edu.uniquindio.proyecto.domain.exception.UsuarioNoEncontradoException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ObtenerUsuarioByEmailUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ObtenerUsuarioByEmailUseCase obtenerUsuarioByEmailUseCase;

    @Test
    void deberiaLanzarExceptionCuandoNoExistaByEmail() {


        String emailString = "noexiste@gmail.com";

        when(usuarioRepository.obtenerPorEmail(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsuarioNoEncontradoException.class, () -> {
            obtenerUsuarioByEmailUseCase.ejecutar(emailString);
        });

        // Verify: Verifica que se llamó al repositorio
        verify(usuarioRepository).obtenerPorEmail(any());
    }
}
package co.edu.uniquindio.proyecto.application.usecase;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.ObtenerUsuarioUseCase;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObtenerUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ObtenerUsuarioUseCase obtenerUsuarioUseCase;

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {

        when(usuarioRepository.obtenerPorIdentificacion(any())).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class, () -> {
            obtenerUsuarioUseCase.ejecutar("CEDULA_CIUDADANIA", "999");
        });
    }
}

package co.edu.uniquindio.proyecto.application.usecase;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.DesactivarUsuarioUseCase;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.desactivarusuario.DesactivarUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DesactivarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private DesactivarUsuarioService desactivarUsuarioService;

    @InjectMocks
    private DesactivarUsuarioUseCase desactivarUsuarioUseCase;

    @Test
    void deberiaDesactivarUsuarioCuandoNoTieneSolicitudesActivas() {

        String idNumero = "12345";
        DocumentoIdentidad id = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, idNumero);
        Usuario usuario = Usuario.crear(id, "Test", new Email("test@test.com"), TipoUsuario.ESTUDIANTE);

        when(usuarioRepository.obtenerPorIdentificacion(any())).thenReturn(Optional.of(usuario));

        desactivarUsuarioUseCase.ejecutar("CEDULA_CIUDADANIA", idNumero, false);
        verify(usuarioRepository).obtenerPorIdentificacion(any());
        verify(desactivarUsuarioService).desactivarUsuario(usuario, false);
        verify(usuarioRepository).crearUsuario(usuario);
    }

    @Test
    void deberiaLanzarExcepcionCuandoReglaDominioFalla() {

        String idNumero = "12345";
        DocumentoIdentidad id = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, idNumero);
        Usuario usuario = Usuario.crear(id, "Test", new Email("test@test.com"), TipoUsuario.ESTUDIANTE);

        when(usuarioRepository.obtenerPorIdentificacion(any())).thenReturn(Optional.of(usuario));

        doThrow(new ReglaDominioException("No se puede desactivar..."))
                .when(desactivarUsuarioService).desactivarUsuario(any(), eq(true));

        assertThrows(ReglaDominioException.class, () -> {
            desactivarUsuarioUseCase.ejecutar("CEDULA_CIUDADANIA", idNumero, true);
        });

        verify(usuarioRepository, never()).crearUsuario(any());
    }
}

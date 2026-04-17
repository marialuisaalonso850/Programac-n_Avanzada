package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.exception.UsuarioNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ObtenerUsuarioByEmailUseCase {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Usuario ejecutar(String email) {
        return usuarioRepository.obtenerPorEmail(new Email(email))
                .orElseThrow(() -> new UsuarioNoEncontradoException("No existe un usuario registrado con el email: " + email));
    }
}
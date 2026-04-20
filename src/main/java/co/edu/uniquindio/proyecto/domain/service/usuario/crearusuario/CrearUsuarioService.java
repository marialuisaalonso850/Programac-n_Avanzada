package co.edu.uniquindio.proyecto.domain.service.usuario.crearusuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void validarUsuario(Usuario usuario) {

        if (usuarioRepository.existePorIdentificacion(usuario.getIdentificacion())){
            throw new ReglaDominioException("Ya existe un usuario con esa identificación");
        }

        if (usuarioRepository.existePorEmail(usuario.getEmail())) {
            throw new ReglaDominioException("El email ya se encuentra registrado");
        }
    }
}

package co.edu.uniquindio.proyecto.domain.service.usuario.crearusuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrearUsuarioService {

    private final UsuarioRepository usuarioRepository;
//un service puede usar otro service o debe ser repository?
    public void crearUsuario(Usuario usuario) {

        // Valida si ya existe por ID
        if (usuarioRepository.existePorIdentificacion(usuario.getIdentificacion())){
            throw new ReglaDominioException("Ya existe un usuario con esa identificación");
        }

        // Valida si el email ya está en uso
        if (usuarioRepository.existePorEmail(usuario.getEmail())) {
            throw new ReglaDominioException("El email ya se encuentra registrado");
        }
        usuarioRepository.crearUsuario(usuario);
    }

}

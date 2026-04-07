package co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObtenerUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario obtenerUsuario(DocumentoIdentidad id) {
        return usuarioRepository.obtenerPorIdentificacion(id)
                .orElseThrow(() -> new ReglaDominioException("No se encontró el usuario"));
    }
}

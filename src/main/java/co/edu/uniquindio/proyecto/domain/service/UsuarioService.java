package co.edu.uniquindio.proyecto.domain.service;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void desactivarUsuario(Usuario usuario, boolean tieneSolicitudesActivas) {

        if (tieneSolicitudesActivas) {
            throw new ReglaDominioException(
                    "No se puede desactivar un usuario con solicitudes activas"
            );
        }

        usuario.desactivar();
    }

    public void ejecutar(DocumentoIdentidad id, String nombre, Email email, TipoUsuario tipo) {

        // 1. Validar si ya existe por ID
        if (usuarioRepository.existePorIdentificacion(id)) {
            throw new ReglaDominioException("Ya existe un usuario con esa identificación");
        }

        // 2. Validar si el email ya está en uso
        if (usuarioRepository.existePorEmail(email)) {
            throw new ReglaDominioException("El email ya se encuentra registrado");
        }

        Usuario nuevoUsuario = Usuario.crear(id, nombre, email, tipo);

        // 4. Persistir
        usuarioRepository.guardar(nuevoUsuario);
    }
}
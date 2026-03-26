package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;
import lombok.RequiredArgsConstructor;


public class CrearUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public CrearUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void ejecutar(DocumentoIdentidad id, String nombre, Email email, TipoUsuario tipo) {

        if (usuarioRepository.existePorIdentificacion(id)) {
            throw new ReglaDominioException("Usuario ya existe");
        }

        Usuario nuevo = Usuario.crear(id, nombre, email, tipo);
        usuarioRepository.guardar(nuevo);
    }

    private void validarExistencia(DocumentoIdentidad id, Email email) {
        if (usuarioRepository.existePorIdentificacion(id)) {
            throw new ReglaDominioException("Ya existe un usuario con esa identificación: ");
        }

        if (usuarioRepository.existePorEmail(email)) {
            throw new ReglaDominioException("El email " + email.getValor() + " ya está registrado");
        }
    }
}
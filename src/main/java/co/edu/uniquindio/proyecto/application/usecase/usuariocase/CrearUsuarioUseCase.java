package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.UsuarioYaExisteException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Inyectado desde SecurityConfig

    public Usuario ejecutar(DocumentoIdentidad id, String nombre, Email email,
                            TipoUsuario tipo, String passwordPlano) {

        // 1. Validar si ya existe (Regla de negocio)
        if (usuarioRepository.existePorIdentificacion(id)) {
            throw new UsuarioYaExisteException("La identificación ya está registrada");
        }

        // 2. Encriptar la contraseña antes de pasar a infraestructura
        String passwordEncriptado = passwordEncoder.encode(passwordPlano);

        Usuario nuevoUsuario = new Usuario(id, nombre, email, tipo, passwordEncriptado);

        // 3. Persistir
        usuarioRepository.crearUsuario(nuevoUsuario, passwordEncriptado);

        return nuevoUsuario;
    }
}
package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.application.dto.request.usuario.auth.LoginRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.auth.LoginResponse;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.EntidadNoEncontradaException;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import co.edu.uniquindio.proyecto.infraestructure.rest.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse ejecutar(LoginRequest request) {

        DocumentoIdentidad id = new DocumentoIdentidad(
                request.tipoDocumento(),
                request.identificacion()
        );

        // Verificar que existe
        Usuario usuario = usuarioRepository.obtenerPorIdentificacion(id)
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "Usuario no encontrado"));

        // Verificar que está activo
        if (!usuario.estaActivo())
            throw new ReglaDominioException("El usuario está inactivo");

        // Verificar password
        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new ReglaDominioException("Credenciales incorrectas");
        }


        // Generar token
        String token = jwtService.generarToken(usuario);

        return new LoginResponse(
                token,
                usuario.getTipoUsuario().name(),
                usuario.getNombre()
        );
    }
}
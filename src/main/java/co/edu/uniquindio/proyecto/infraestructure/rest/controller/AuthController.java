package co.edu.uniquindio.proyecto.infraestructure.rest.controller;

import co.edu.uniquindio.proyecto.application.dto.request.usuario.auth.LoginRequest;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.infraestructure.rest.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Autenticación")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {

        // 1. Autenticar con Spring Security
        // Esto verifica que la identificación y el password (BCrypt) coincidan
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.identificacion(),
                        request.password()
                )
        );

        // 2. Obtener el usuario del dominio para generar el token con sus claims
        // Tu JwtService.generarToken(Usuario usuario) necesita el objeto Usuario
        Usuario usuario = usuarioRepository.obtenerPorIdentificacion(
                new DocumentoIdentidad(request.tipoDocumento(), request.identificacion())
        ).orElseThrow();

        // 3. Generar el JWT usando tu JwtService
        String token = jwtService.generarToken(usuario);

        // 4. Responder con el Token
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
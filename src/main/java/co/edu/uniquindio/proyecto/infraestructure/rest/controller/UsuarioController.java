package co.edu.uniquindio.proyecto.infraestructure.rest.controller;

import co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario.CrearUsuarioRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario.DetalleUsuarioResponse;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.CrearUsuarioUseCase;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.DesactivarUsuarioUseCase;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.ObtenerUsuarioUseCase;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios")
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private final DesactivarUsuarioUseCase desactivarUsuarioUseCase;
    private final UsuarioMapper mapper;

    /**
     * PÚBLICO: No lleva @SecurityRequirement.
     * Permite que nuevos usuarios se registren sin token.
     */
    @PostMapping
    @Operation(summary = "Registrar un nuevo usuario (Abierto)")
    public ResponseEntity<DetalleUsuarioResponse> crear(
            @Valid @RequestBody CrearUsuarioRequest request){

        // Convertimos el request (DTO) a dominio para procesar
        Usuario usuarioDomain = mapper.toDomain(request);

        // Ejecutamos lógica de negocio (encriptación y guardado)
        Usuario usuario = crearUsuarioUseCase.ejecutar(
                usuarioDomain.getIdentificacion(),
                usuarioDomain.getNombre(),
                usuarioDomain.getEmail(),
                usuarioDomain.getTipoUsuario(),
                usuarioDomain.getPassword() // Se encriptará dentro del UseCase
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getIdentificacion().numero())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDetalleResponse(usuario));
    }

    /**
     * PROTEGIDO: Solo usuarios autenticados pueden ver perfiles.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth") // APARECE EL CANDADO EN SWAGGER
    @Operation(summary = "Obtener detalle de un usuario por ID")
    public ResponseEntity<DetalleUsuarioResponse> obtenerPorId(
            @PathVariable String id,
            @RequestParam(defaultValue = "CEDULA_CIUDADANIA") String tipoDocumento) {

        Usuario usuario = obtenerUsuarioUseCase.ejecutar(tipoDocumento, id);
        return ResponseEntity.ok(mapper.toDetalleResponse(usuario));
    }

    /**
     * PROTEGIDO: Solo personal autorizado puede desactivar cuentas.
     */
    @PatchMapping("/{tipoDocumento}/{id}/desactivar")
    @SecurityRequirement(name = "bearerAuth") // APARECE EL CANDADO EN SWAGGER
    @Operation(summary = "Desactivar una cuenta de usuario")
    public ResponseEntity<Void> desactivar(
            @PathVariable String tipoDocumento,
            @PathVariable String id) {

        desactivarUsuarioUseCase.ejecutar(tipoDocumento, id);
        return ResponseEntity.noContent().build(); // 204 No Content es más apropiado para Void
    }
}
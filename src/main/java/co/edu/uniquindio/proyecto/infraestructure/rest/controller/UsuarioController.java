package co.edu.uniquindio.proyecto.infraestructure.rest.controller;
import co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario.CrearUsuarioRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario.DetalleUsuarioResponse;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.*;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private final ConsultarUsuariosUseCase consultarUsuariosUseCase;
    private final ObtenerUsuarioByEmailUseCase obtenerUsuarioByEmailUseCase;
    private final DesactivarUsuarioUseCase desactivarUsuarioUseCase;
    private final UsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<DetalleUsuarioResponse> crear(
            @Valid @RequestBody CrearUsuarioRequest request){

        // Convertir DTO a dominio
        Usuario usuarioDomain = mapper.toDomain(request);

        //Pasar SOLO dominio al use case
        Usuario usuario = crearUsuarioUseCase.ejecutar(
                usuarioDomain.getIdentificacion(),
                usuarioDomain.getNombre(),
                usuarioDomain.getEmail(),
                usuarioDomain.getTipoUsuario()
        );

        //Convertir dominio a response
        DetalleUsuarioResponse response = mapper.toDetalleResponse(usuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getIdentificacion().numero())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetalleUsuarioResponse> obtenerPorId(
            @PathVariable String id,
            @RequestParam(defaultValue = "CEDULA_CIUDADANIA") String tipoDocumento) {

        // Llamamos al Use Case
        Usuario usuario = obtenerUsuarioUseCase.ejecutar(tipoDocumento, id);

        // Convertimos el dominio a DTO de respuesta usando el mapper que ya tienes
        return ResponseEntity.ok(mapper.toDetalleResponse(usuario));
    }

    @GetMapping
    public ResponseEntity<List<DetalleUsuarioResponse>> listarTodos() {
        // 2. Llamar al Use Case
        List<Usuario> usuarios = consultarUsuariosUseCase.ejecutar();

        // 3. Mapear la lista de dominio a lista de DTOs
        List<DetalleUsuarioResponse> response = usuarios.stream()
                .map(mapper::toDetalleResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/por-email")
    public ResponseEntity<DetalleUsuarioResponse> obtenerPorEmail(@RequestParam String email) {
        Usuario usuario = obtenerUsuarioByEmailUseCase.ejecutar(email);
        return ResponseEntity.ok(mapper.toDetalleResponse(usuario));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(
            @PathVariable String id,
            @RequestParam(defaultValue = "CEDULA_CIUDADANIA") String tipoDocumento,
            @RequestParam boolean tieneSolicitudesActivas) {

        desactivarUsuarioUseCase.ejecutar(tipoDocumento, id, tieneSolicitudesActivas);

        return ResponseEntity.noContent().build();
    }

}


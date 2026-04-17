package co.edu.uniquindio.proyecto.infraestructure.rest.controller;
import co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario.CrearUsuarioRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario.DetalleUsuarioResponse;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.CrearUsuarioUseCase;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.ObtenerUsuarioUseCase;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
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

}


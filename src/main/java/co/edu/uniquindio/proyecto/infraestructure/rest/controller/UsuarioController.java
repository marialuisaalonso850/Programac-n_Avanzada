package co.edu.uniquindio.proyecto.infraestructure.rest.controller;
import co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario.CrearUsuarioRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario.DetalleUsuarioResponse;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.CrearUsuarioUseCase;
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

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
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

}


package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.EntidadNoEncontradaException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObtenerUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public Usuario ejecutar(String tipoDocumento, String numero) {
        DocumentoIdentidad id = new DocumentoIdentidad(
                TipoDocumento.valueOf(tipoDocumento.toUpperCase()),
                numero
        );

        return usuarioRepository.obtenerPorIdentificacion(id)
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "Usuario no encontrado con identificación: " + numero
                ));
    }
}
package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.UsuarioNoEncontradoException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ObtenerUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Usuario ejecutar(String tipoDocumento, String numero) {
        DocumentoIdentidad id = new DocumentoIdentidad(
                TipoDocumento.valueOf(tipoDocumento.toUpperCase()),
                numero
        );

        return usuarioRepository.obtenerPorIdentificacion(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("El usuario con identificación " + numero + " no existe en el sistema."));
    }
}
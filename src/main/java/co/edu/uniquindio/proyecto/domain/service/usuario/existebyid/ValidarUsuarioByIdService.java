package co.edu.uniquindio.proyecto.domain.service.usuario.existebyid;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import lombok.RequiredArgsConstructor;
import java.util.Objects;

@RequiredArgsConstructor
public class ValidarUsuarioByIdService {

    private final UsuarioRepository usuarioRepository;

    public void ejecutar(DocumentoIdentidad id){

        Objects.requireNonNull(id, "El documento de identidad no puede ser nulo");

        if (usuarioRepository.existePorIdentificacion(id)) {
            throw new ReglaDominioException("Ya existe un usuario con esa identificación");
        }
    }
}

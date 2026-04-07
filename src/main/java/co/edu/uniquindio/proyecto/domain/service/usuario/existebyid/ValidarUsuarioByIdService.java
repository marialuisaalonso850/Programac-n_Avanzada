package co.edu.uniquindio.proyecto.domain.service.usuario.existebyid;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExisteByIdService {

    private final UsuarioRepository usuarioRepository;

    public void existeById(DocumentoIdentidad id){

        if (usuarioRepository.existePorIdentificacion(id)) {
            throw new ReglaDominioException("Ya existe un usuario con esa identificación");
        }
    }
}

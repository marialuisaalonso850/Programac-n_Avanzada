package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.crearusuario.CrearUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CrearUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario ejecutar(DocumentoIdentidad id, String nombre, Email email, TipoUsuario tipo) {
        Usuario nuevo = Usuario.crear(id, nombre, email, tipo);

        usuarioRepository.crearUsuario(nuevo);

        return nuevo;
    }
}
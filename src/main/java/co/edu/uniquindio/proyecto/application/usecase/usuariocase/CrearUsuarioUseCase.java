package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.crearusuario.CrearUsuarioService;
import co.edu.uniquindio.proyecto.domain.service.usuario.existebyid.ValidarUsuarioByIdService;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrearUsuarioUseCase {

    private final CrearUsuarioService crearUsuarioService;

    public void ejecutar(DocumentoIdentidad id, String nombre, Email email, TipoUsuario tipo) {
        Usuario nuevo = Usuario.crear(id, nombre, email, tipo);
        crearUsuarioService.crearUsuario(nuevo);
    }

}
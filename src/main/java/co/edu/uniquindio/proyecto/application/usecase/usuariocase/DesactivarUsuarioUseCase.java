package co.edu.uniquindio.proyecto.application.usecase.usuariocase;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.desactivarusuario.DesactivarUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import co.edu.uniquindio.proyecto.domain.exception.UsuarioNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DesactivarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final DesactivarUsuarioService desactivarUsuarioService;

    @Transactional
    public void ejecutar(String tipoDocumento, String numero, boolean tieneSolicitudesActivas) {

        DocumentoIdentidad id = new DocumentoIdentidad(
                TipoDocumento.valueOf(tipoDocumento.toUpperCase()),
                numero
        );

        // 1. Buscar al usuario
        Usuario usuario = usuarioRepository.obtenerPorIdentificacion(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        // 2. Usar el Servicio de Dominio para aplicar la lógica de negocio
        desactivarUsuarioService.desactivarUsuario(usuario, tieneSolicitudesActivas);

        // 3. Persistir el cambio de estado
        usuarioRepository.crearUsuario(usuario); // En el repo de memoria, el put reemplaza al existente
    }
}
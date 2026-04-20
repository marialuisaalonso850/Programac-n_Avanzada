package co.edu.uniquindio.proyecto.domain.repository.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository {

    void crearUsuario(Usuario usuario, String passwordEncriptado);
    void actualizarUsuario(Usuario usuario);

    Optional<Usuario> obtenerPorIdentificacion(DocumentoIdentidad id);

    Optional<Usuario> obtenerPorEmail(Email email);

    boolean existePorIdentificacion(DocumentoIdentidad id);

    boolean existePorEmail(Email email);
    List<Usuario> obtenerTodos();

    List<Usuario> obtenerUsuariosPaginados(int pagina, int tamaño);

    List<Usuario> obtenerUsuariosPorEstadoPaginado(String estado, int pagina, int tamaño);
}
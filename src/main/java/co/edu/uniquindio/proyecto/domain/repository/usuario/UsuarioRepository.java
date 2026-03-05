package co.edu.uniquindio.proyecto.domain.repository.usuario;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;

import java.util.Optional;
import java.util.List;

public interface UsuarioRepository {

    void guardar(Usuario usuario);

    Optional<Usuario> obtenerPorIdentificacion(DocumentoIdentidad id);

    List<Usuario> obtenerTodos();
}
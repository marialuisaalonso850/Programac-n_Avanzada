package co.edu.uniquindio.proyecto.infraestructure.repository;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;

import java.util.*;

public class UsuarioRepositoryEnMemoria implements UsuarioRepository {

    private final Map<DocumentoIdentidad, Usuario> baseDatos = new HashMap<>();

    @Override
    public void guardar(Usuario usuario) {
        baseDatos.put(usuario.getIdentificacion(), usuario);
    }

    @Override
    public Optional<Usuario> obtenerPorIdentificacion(DocumentoIdentidad id) {
        return Optional.ofNullable(baseDatos.get(id));
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(baseDatos.values());
    }
}
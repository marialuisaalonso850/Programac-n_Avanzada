package co.edu.uniquindio.proyecto.infraestructure.repository;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email; // Importante importar el VO
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
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
    public Optional<Usuario> obtenerPorEmail(Email email) {
        return baseDatos.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public boolean existePorIdentificacion(DocumentoIdentidad id) {
        return baseDatos.containsKey(id);
    }

    @Override
    public boolean existePorEmail(Email email) {
        return baseDatos.values().stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }

    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(baseDatos.values());
    }
}
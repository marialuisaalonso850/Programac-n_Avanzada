package co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.adaptador;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.UsuarioEntity;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.mapper.UsuarioEntityMapper;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.jpadata.UsuarioJpaDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
@Transactional
public class UsuarioJpaRepository implements UsuarioRepository {

    private final UsuarioJpaDataRepository dataRepository;
    private final UsuarioEntityMapper mapper;

    @Override
    public void crearUsuario(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        dataRepository.save(entity);
    }

    @Override
    public Optional<Usuario> obtenerPorIdentificacion(DocumentoIdentidad id) {
        return dataRepository.findById(id.numero())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> obtenerPorEmail(Email email) {
        return dataRepository.findByEmail(email.valor())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existePorIdentificacion(DocumentoIdentidad id) {
        return dataRepository.existsById(id.numero());
    }

    @Override
    public boolean existePorEmail(Email email) {
        return dataRepository.findByEmail(email.valor()).isPresent();
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return dataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
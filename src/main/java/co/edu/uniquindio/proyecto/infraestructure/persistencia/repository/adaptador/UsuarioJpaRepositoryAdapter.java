package co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.adaptador;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.UsuarioEntity;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.mapper.UsuarioPersistenceMapper;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.jpadata.UsuarioJpaDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioJpaRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaDataRepository jpaDataRepository;
    private final UsuarioPersistenceMapper mapper;

    @Override
    @Transactional
    public void crearUsuario(Usuario usuario, String passwordEncriptado) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        entity.setPassword(passwordEncriptado);
        jpaDataRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorIdentificacion(DocumentoIdentidad id) {
        return jpaDataRepository.findById(id.numero())
                .map(mapper::toDomain);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorEmail(Email email) {
        return jpaDataRepository.findByEmail(email.valor())
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorIdentificacion(DocumentoIdentidad id) {
        return jpaDataRepository.existsById(id.numero());
    }

    @Override
    public boolean existePorEmail(Email email) {
        return jpaDataRepository.existsByEmail(email.valor());
    }

    @Override
    @Transactional
    public void actualizarUsuario(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        jpaDataRepository.save(entity);
    }

}
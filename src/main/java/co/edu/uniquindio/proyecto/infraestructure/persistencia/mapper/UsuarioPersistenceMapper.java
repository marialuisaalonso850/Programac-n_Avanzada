package co.edu.uniquindio.proyecto.infraestructure.persistencia.mapper;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioPersistenceMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        return UsuarioEntity.builder()
                .identificacion(usuario.getIdentificacion().numero())
                .tipoDocumento(usuario.getIdentificacion().tipo().name())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail().valor())
                .tipoUsuario(usuario.getTipoUsuario().name())
                .estado(usuario.getEstado().name())
                .password(usuario.getPassword()) // ¡CRÍTICO: Guardar el password!
                .build();
    }

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;

        Usuario usuario = new Usuario(
                new DocumentoIdentidad(
                        TipoDocumento.valueOf(entity.getTipoDocumento()),
                        entity.getIdentificacion()
                ),
                entity.getNombre(),
                new Email(entity.getEmail()),
                TipoUsuario.valueOf(entity.getTipoUsuario()),
                entity.getPassword(),
                EstadoUsuario.valueOf(entity.getEstado())
        );

        return usuario;
    }
}
package co.edu.uniquindio.proyecto.infraestructure.rest.mapper;

import co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario.CrearUsuarioRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario.DetalleUsuarioResponse;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "identificacion", expression = "java(mapToDocumento(request))")
    @Mapping(target = "email", expression = "java(mapToEmail(request))")
    @Mapping(target = "tipoUsuario", expression = "java(mapToTipoUsuario(request.tipoUsuario()))")
    @Mapping(target = "estado", ignore = true)
    Usuario toDomain(CrearUsuarioRequest request);

    @Mapping(target = "identificacion", source = "identificacion.numero")
    @Mapping(target = "email", source = "email.valor")
    @Mapping(target = "activo", expression = "java(usuario.getEstado() == co.edu.uniquindio.proyecto.domain.valueobject.EstadoUsuario.ACTIVO)")
    DetalleUsuarioResponse toDetalleResponse(Usuario usuario);

    default DocumentoIdentidad mapToDocumento(CrearUsuarioRequest request) {
        return new DocumentoIdentidad(
                request.tipoDocumento(),
                request.identificacion()
        );
    }

    default Email mapToEmail(CrearUsuarioRequest request) {
        return new Email(request.email());
    }

    default TipoUsuario mapToTipoUsuario(String tipo) {
        return TipoUsuario.valueOf(tipo.toUpperCase());
    }
}
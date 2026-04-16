package co.edu.uniquindio.proyecto.infraestructure.rest.mapper;

import co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario.CrearUsuarioRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario.DetalleUsuarioResponse;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "identificacion", source = "request")
    @Mapping(target = "email", source = "request")
    @Mapping(target = "estado", ignore = true) // El constructor de Usuario lo pone ACTIVO por defecto
    Usuario toDomain(CrearUsuarioRequest request);

    @Mapping(target = "identificacion", source = "identificacion.numero")
    @Mapping(target = "email", source = "email.valor")
    @Mapping(target = "activo", constant = "true") // O usa una lógica para usuario.estaActivo()
    DetalleUsuarioResponse toDetalleResponse(Usuario usuario);

    // MÉTODOS DE APOYO: Aquí le explicas a MapStruct cómo crear tus Value Objects
    default DocumentoIdentidad mapToDocumento(CrearUsuarioRequest request) {
        return new DocumentoIdentidad(
                TipoDocumento.valueOf(request.tipoDocumento().toUpperCase()),
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
package co.edu.uniquindio.proyecto.infraestructure.rest.mapper;

import co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario.CrearUsuarioRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario.DetalleUsuarioResponse;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-16T12:01:50-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Eclipse Adoptium)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toDomain(CrearUsuarioRequest request) {
        if ( request == null ) {
            return null;
        }

        DocumentoIdentidad identificacion = null;
        Email email = null;
        String nombre = null;
        TipoUsuario tipoUsuario = null;

        identificacion = mapToDocumento( request );
        email = mapToEmail( request );
        nombre = request.nombre();
        tipoUsuario = mapToTipoUsuario( request.tipoUsuario() );

        Usuario usuario = new Usuario( identificacion, nombre, email, tipoUsuario );

        return usuario;
    }

    @Override
    public DetalleUsuarioResponse toDetalleResponse(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        String identificacion = null;
        String email = null;
        String nombre = null;
        String tipoUsuario = null;

        identificacion = usuarioIdentificacionNumero( usuario );
        email = usuarioEmailValor( usuario );
        nombre = usuario.getNombre();
        if ( usuario.getTipoUsuario() != null ) {
            tipoUsuario = usuario.getTipoUsuario().name();
        }

        boolean activo = true;

        DetalleUsuarioResponse detalleUsuarioResponse = new DetalleUsuarioResponse( identificacion, nombre, email, tipoUsuario, activo );

        return detalleUsuarioResponse;
    }

    private String usuarioIdentificacionNumero(Usuario usuario) {
        DocumentoIdentidad identificacion = usuario.getIdentificacion();
        if ( identificacion == null ) {
            return null;
        }
        return identificacion.numero();
    }

    private String usuarioEmailValor(Usuario usuario) {
        Email email = usuario.getEmail();
        if ( email == null ) {
            return null;
        }
        return email.valor();
    }
}

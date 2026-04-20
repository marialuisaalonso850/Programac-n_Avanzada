package co.edu.uniquindio.proyecto.infraestructure.rest.security.helper;

import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAutenticado {

    public DocumentoIdentidad getDocumentoIdentidad() {
        String usuarioId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, usuarioId);
    }

    public String getTipoUsuario() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();
    }
}

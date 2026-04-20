package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.EntidadNoEncontradaException;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.SolicitudService.AsignarResponsableService;
import co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuariobyid.ObtenerUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.rest.security.helper.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AsignarResponsableUseCase {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsignarResponsableService dominioService;
    private final UsuarioAutenticado authHelper;

    @Transactional
    public Solicitud ejecutar(String solicitudId, String responsableId) {

        DocumentoIdentidad idGestor = authHelper.getDocumentoIdentidad();
        Usuario gestor = usuarioRepository.obtenerPorIdentificacion(idGestor)
                .orElseThrow(() -> new ReglaDominioException("Gestor no encontrado"));

        Usuario responsable = usuarioRepository.obtenerPorIdentificacion(new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, responsableId))
                .orElseThrow(() -> new ReglaDominioException("El responsable elegido no existe"));

        // 3. Sobre qué solicitud
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new ReglaDominioException("Solicitud no encontrada"));

        // 4. Delegamos al servicio de dominio (Valida ROL y asocia)
        dominioService.ejecutar(solicitud, gestor, responsable);

        // 5. Persistimos
        solicitudRepository.save(solicitud);

        return solicitud;
    }
}
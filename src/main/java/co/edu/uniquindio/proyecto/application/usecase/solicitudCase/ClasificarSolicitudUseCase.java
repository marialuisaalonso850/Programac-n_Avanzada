package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud.ClasificarSolicitudRequest;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.SolicitudService.ClasificarSolicitudService;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.infraestructure.rest.security.helper.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ClasificarSolicitudUseCase {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClasificarSolicitudService dominioService;
    private final UsuarioAutenticado authHelper;

    @Transactional
    public Solicitud ejecutar(String solicitudId, ClasificarSolicitudRequest request) {
        DocumentoIdentidad idActor = authHelper.getDocumentoIdentidad();

        Usuario actor = usuarioRepository.obtenerPorIdentificacion(idActor)
                .orElseThrow(() -> new ReglaDominioException("Usuario no encontrado"));

        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new ReglaDominioException("Solicitud no existe"));

        dominioService.ejecutar(
                solicitud,
                request.tipoSolicitud(),
                request.prioridad(),
                actor,
                request.justificacion()
        );

        solicitudRepository.save(solicitud);

        return solicitud;
    }
}
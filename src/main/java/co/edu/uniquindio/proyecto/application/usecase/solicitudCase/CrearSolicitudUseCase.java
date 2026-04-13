package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.crearsolicitud.CrearSolicitudRequest;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuariobyid.ObtenerUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import lombok.RequiredArgsConstructor; //
import org.springframework.stereotype.Service; //
import org.springframework.transaction.annotation.Transactional; //

import java.util.UUID;

@Service
@RequiredArgsConstructor // Genera el constructor para inyectar dependencias automáticamente
public class CrearSolicitudUseCase {

    private final SolicitudRepository solicitudRepository;
    private final ObtenerUsuarioService usuarioService;

    @Transactional
    public Solicitud ejecutar(CrearSolicitudRequest request) {

        TipoDocumento tipoDoc = TipoDocumento.valueOf(request.tipoDocumento());
        DocumentoIdentidad documento = new DocumentoIdentidad(tipoDoc, request.solicitanteId());

        Usuario usuario = usuarioService.obtenerUsuario(documento);

        Solicitud solicitud = new Solicitud(
                new SolicitudId(UUID.randomUUID()),
                new Descripcion(request.descripcion()),
                usuario,
                Prioridad.MEDIA,
                request.canalOrigen()
        );

        return solicitudRepository.save(solicitud);
    }

    @Transactional
    public Solicitud ejecutar(SolicitudId solId, Descripcion desc, DocumentoIdentidad clienteId, Prioridad prioridad, CanalOrigen canalOrigen) {
        Usuario usuario = usuarioService.obtenerUsuario(clienteId);
        Solicitud solicitud = new Solicitud(
                solId,
                desc,
                usuario,
                prioridad,
                canalOrigen
        );

        return solicitudRepository.save(solicitud);
    }
}

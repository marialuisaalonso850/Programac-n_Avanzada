package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.crearsolicitud.CrearSolicitudRequest;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuariobyid.ObtenerUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrearSolicitudUseCase {

    private final SolicitudRepository solicitudRepository;
    private final ObtenerUsuarioService usuarioService;

    @Transactional
    public Solicitud ejecutar(CrearSolicitudRequest request, DocumentoIdentidad documentoSolicitante) {

        Usuario usuario = usuarioService.obtenerUsuario(documentoSolicitante);

        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion(request.descripcion()),
                usuario,
                Prioridad.MEDIA,
                request.canalOrigen()
        );

        solicitudRepository.save(solicitud);
        return solicitud;
    }
}
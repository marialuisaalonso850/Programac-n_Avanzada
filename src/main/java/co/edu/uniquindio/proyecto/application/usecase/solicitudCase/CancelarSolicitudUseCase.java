package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.EntidadNoEncontradaException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuariobyid.ObtenerUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelarSolicitudUseCase {

    private final SolicitudRepository repository;
    private final ObtenerUsuarioService usuarioService;

    @Transactional
    public Solicitud ejecutar(String id, DocumentoIdentidad actorId, String motivo) {

        Solicitud solicitud = repository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "Solicitud no encontrada: " + id));

        Usuario actor = usuarioService.obtenerUsuario(actorId);
        solicitud.cancelar(actor, motivo);
        repository.save(solicitud);
        return solicitud;
    }
}
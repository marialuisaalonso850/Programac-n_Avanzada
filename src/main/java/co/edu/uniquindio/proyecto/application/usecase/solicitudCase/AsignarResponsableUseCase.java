package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuariobyid.ObtenerUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // [cite: 95]

@Service // [cite: 96]
@RequiredArgsConstructor // [cite: 97]
public class AsignarResponsableUseCase {

    private final SolicitudRepository solicitudRepository;
    private final ObtenerUsuarioService usuarioService;

    /**
     * @Transactional asegura que:
     * 1. Se inicie una transacción al entrar.
     * 2. Se haga commit si completa exitosamente.
     * 3. Se haga rollback si lanza una excepción (ej. Solicitud no encontrada).
     */
    @Transactional // [cite: 101, 151]
    public Solicitud ejecutar(SolicitudId solicitudId, DocumentoIdentidad responsableId, DocumentoIdentidad gestorId) {

        Solicitud solicitud = solicitudRepository.findById(solicitudId.getValue().toString())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // 2. Obtener los usuarios involucrados
        Usuario responsable = usuarioService.obtenerUsuario(responsableId);
        Usuario gestor = usuarioService.obtenerUsuario(gestorId);

        solicitud.asignarResponsable(responsable, gestor);

        return solicitudRepository.save(solicitud);
    }
}
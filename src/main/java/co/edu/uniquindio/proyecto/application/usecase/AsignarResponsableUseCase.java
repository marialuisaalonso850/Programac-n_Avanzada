package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.*;


public class AsignarResponsableUseCase {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;

    public AsignarResponsableUseCase(SolicitudRepository solicitudRepository,
                                     UsuarioRepository usuarioRepository) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void ejecutar(SolicitudId solicitudId,
                         DocumentoIdentidad responsableId) {

        Solicitud solicitud = solicitudRepository.obtenerPorId(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Usuario responsable = usuarioRepository
                .obtenerPorIdentificacion(responsableId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        solicitud.asignarResponsable(responsable);

        solicitudRepository.guardar(solicitud);
    }
}
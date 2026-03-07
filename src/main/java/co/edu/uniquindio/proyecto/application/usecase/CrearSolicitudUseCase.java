package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.*;

public class CrearSolicitudUseCase {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;

    public CrearSolicitudUseCase(SolicitudRepository solicitudRepository, UsuarioRepository usuarioRepository) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void ejecutar(SolicitudId id, Descripcion descripcion, TipoSolicitud tipo,
                         DocumentoIdentidad usuarioId, Prioridad prioridad, CanalOrigen canal) {

        Usuario usuario = usuarioRepository
                .obtenerPorIdentificacion(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Solicitud solicitud = new Solicitud(
                id,
                descripcion,
                tipo,
                usuario,
                prioridad,
                canal
        );

        solicitudRepository.guardar(solicitud);
    }
}
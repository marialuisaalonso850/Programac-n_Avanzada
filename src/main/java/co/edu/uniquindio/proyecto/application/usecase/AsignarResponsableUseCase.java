package co.edu.uniquindio.proyecto.application.usecase;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuariobyid.ObtenerUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AsignarResponsableUseCase {

    private final SolicitudRepository solicitudRepository;
    private final ObtenerUsuarioService usuarioService;

    public void ejecutar(SolicitudId solicitudId,
                         DocumentoIdentidad responsableId) {

        Solicitud solicitud = solicitudRepository.obtenerPorId(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Usuario responsable = usuarioService
                .obtenerUsuario(responsableId);

        solicitud.asignarResponsable(responsable);

        solicitudRepository.guardar(solicitud);
    }
}
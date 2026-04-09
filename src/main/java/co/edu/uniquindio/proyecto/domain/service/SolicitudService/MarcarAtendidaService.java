package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class MarcarAtendidaService {
    public void ejecutar(Solicitud solicitud, Usuario usuarioActual, String observacion) {
        if (!usuarioActual.equals(solicitud.getResponsable()) && !usuarioActual.esAdmin()) {
            throw new ReglaDominioException("Solo el responsable o admin puede marcar como atendida");
        }
        solicitud.marcarComoAtendida(observacion);
    }
}
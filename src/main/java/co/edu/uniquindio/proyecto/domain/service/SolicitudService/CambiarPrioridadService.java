package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;

public class CambiarPrioridadService {
    public void ejecutar(Solicitud solicitud, Prioridad nuevaPrioridad, String justificacion, Usuario usuarioActual) {
        if (!usuarioActual.puedeCambiarPrioridad()) {
            throw new ReglaDominioException("Solo un administrador o coordinador puede cambiar la prioridad");
        }
        solicitud.cambiarPrioridad(nuevaPrioridad, justificacion, usuarioActual);
    }
}
package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import org.springframework.stereotype.Service;

@Service
public class ClasificarSolicitudService {
    // 1. Agregamos Prioridad nuevaPrioridad a los parámetros
    public void ejecutar(Solicitud solicitud, TipoSolicitud nuevoTipo, Prioridad nuevaPrioridad, Usuario usuarioActual, String observacion) {

        if (!usuarioActual.esAdmin() && !usuarioActual.esCoordinador()) {
            throw new ReglaDominioException("Solo un administrador o coordinador puede clasificar");
        }

        // 2. Pasamos la nuevaPrioridad a la entidad
        solicitud.clasificar(nuevoTipo, nuevaPrioridad, usuarioActual, observacion);
    }
}


package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // Agregamos esto para que Spring lo reconozca
public class AsignarResponsableService {

    public void ejecutar(Solicitud solicitud, Usuario usuarioActual, Usuario responsable) {

        // 1. Validación de ROL: Solo jerarquía alta
        if (!usuarioActual.esAdmin() && !usuarioActual.esCoordinador()) {
            throw new ReglaDominioException("Acceso denegado: Solo un administrador o coordinador puede asignar responsables.");
        }

        if (!responsable.puedeSerResponsable()) {
            throw new ReglaDominioException("El usuario '" + responsable.getNombre() + "' no tiene el perfil necesario para ser responsable.");
        }

        // 3. Ejecución de la lógica en la entidad
        solicitud.asignarResponsable(responsable, usuarioActual);

    }
}
package co.edu.uniquindio.proyecto.domain.service.SolicitudService;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CambiarPrioridadService {

    // 1. Inyectamos el repositorio para persistir los cambios
    private final SolicitudRepository solicitudRepository;

    public void ejecutar(Solicitud solicitud, Prioridad nuevaPrioridad, String justificacion, Usuario usuarioActual) {

        // 2. Validación de permisos
        if (!usuarioActual.esAdmin() && !usuarioActual.esCoordinador()) {
            throw new ReglaDominioException("Solo un administrador o coordinador puede cambiar la prioridad");
        }

        // 3. Ejecución del comportamiento en la Entidad
        // Corregido: Pasamos la instancia 'usuarioActual', no el nombre de la clase 'Usuario'
        solicitud.cambiarPrioridad(nuevaPrioridad, usuarioActual, justificacion);

        // 4. Guardar los cambios en el repositorio
        solicitudRepository.save(solicitud);
    }
}
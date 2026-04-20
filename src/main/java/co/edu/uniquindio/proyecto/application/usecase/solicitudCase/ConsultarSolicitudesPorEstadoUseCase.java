package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.PaginaSolicitudes;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.SolicitudResumenResponse;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Historial;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultarSolicitudesPorEstadoUseCase {

    private final SolicitudRepository repository;
    private final SolicitudMapper mapper;

    /**
     * RF-07: Implementación de consulta avanzada con paginación.
     * Basado en Pág. 14-19 de la guía.
     */
    public PaginaSolicitudes ejecutarPaginado(
            EstadoSolicitud estado,
            Prioridad prioridad,
            String solicitanteId,
            int page,
            int size) {

        // 1. Creamos el objeto Pageable (Pág. 14 de la guía)
        // Restamos 1 a 'page' si el frontend envía páginas empezando en 1
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size);

        // 2. Llamamos al repositorio usando el nuevo contrato de Dominio
        // Pasamos 'prioridad' como objeto de valor (Enum), no como String
        Page<Solicitud> resultado = repository.findByFiltros(
                estado,
                prioridad,
                solicitanteId,
                pageable
        );

        // 3. Mapeamos el "Fragmento Seleccionado" (Pág. 19 de la guía)
        // El Mapper ya sabe convertir List<Solicitud> a List<SolicitudResumenResponse>
        List<SolicitudResumenResponse> contenido =
                mapper.toResumenResponseList(resultado.getContent());

        // 4. Retornamos el DTO personalizado con la metadata de paginación
        return new PaginaSolicitudes(
                contenido,
                resultado.getNumber() + 1, // Página actual (humana)
                resultado.getTotalPages()
        );
    }

    public Solicitud obtenerPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReglaDominioException(
                        "La solicitud con ID " + id + " no existe."));
    }

    public List<Historial> obtenerHistorial(String id) {
        return obtenerPorId(id).getHistorial();
    }
}
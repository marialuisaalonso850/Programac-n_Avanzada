package co.edu.uniquindio.proyecto.application.usecase.solicitudCase;

import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.PaginaSolicitudes;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.SolicitudResumenResponse;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Historial;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultarSolicitudesPorEstadoUseCase {

    private final SolicitudRepository repository;
    private final SolicitudMapper mapper;

    public PaginaSolicitudes ejecutarPaginado(EstadoSolicitud estado, int page, int size) {
        List<Solicitud> todas = (estado != null)
                ? repository.findByEstado(estado)
                : repository.findAll();

        int totalElementos = todas.size();
        int inicio = Math.min(page * size, totalElementos);
        int fin = Math.min(inicio + size, totalElementos);

        List<Solicitud> segmento = todas.subList(inicio, fin);
        List<SolicitudResumenResponse> contenido = mapper.toResumenResponseList(segmento);

        int totalPaginas = (int) Math.ceil((double) totalElementos / size);

        return new PaginaSolicitudes(contenido, page, totalPaginas);
    }

    public Solicitud obtenerPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReglaDominioException("La solicitud con ID " + id + " no existe."));
    }

    public List<Historial> obtenerHistorial(String id) {
        Solicitud solicitud = obtenerPorId(id);
        return solicitud.getHistorial();
    }
}
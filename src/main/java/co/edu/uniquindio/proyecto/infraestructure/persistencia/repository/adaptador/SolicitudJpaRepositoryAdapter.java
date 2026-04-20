package co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.adaptador;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.SolicitudEntity;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.jpadata.SolicitudJpaDataRepository;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SolicitudJpaRepositoryAdapter implements SolicitudRepository {

    private final SolicitudJpaDataRepository dataRepository;
    private final SolicitudMapper mapper;

    @Override
    public void save(Solicitud solicitud) {
        SolicitudEntity entity = mapper.toEntity(solicitud);
        dataRepository.save(entity);
    }

    @Override
    public Optional<Solicitud> findById(String id) {
        return dataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Solicitud> findByFiltros(EstadoSolicitud estado, Prioridad prioridad,
                                         String usuarioId, Pageable pageable) {
        String estadoStr = (estado != null) ? estado.name() : null;
        String prioridadStr = (prioridad != null) ? prioridad.name() : null;

        Page<SolicitudEntity> paginaRaw = dataRepository
                .buscarConFiltros(estadoStr, prioridadStr, usuarioId, pageable);

        return paginaRaw.map(mapper::toDomain);
    }

    @Override
    public boolean existeSolicitudActivaPorUsuario(DocumentoIdentidad id) {
        return dataRepository.existeSolicitudActivaPorSolicitante(id.numero());
    }
}
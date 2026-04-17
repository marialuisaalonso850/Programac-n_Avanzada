package co.edu.uniquindio.proyecto.infraestructure.persistencia.repository;

import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.SolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudJpaDataRepository extends JpaRepository<SolicitudEntity, String> {
    List<SolicitudEntity> findByEstado(String estado);
    List<SolicitudEntity> findBySolicitanteId(String solicitanteId);
    List<SolicitudEntity> findByResponsableId(String responsableId);
}
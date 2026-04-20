package co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.jpadata;

import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.SolicitudEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudJpaDataRepository extends JpaRepository<SolicitudEntity, String> {

    @Query("SELECT s FROM SolicitudEntity s WHERE " +
            "(:estado IS NULL OR s.estado = :estado) AND " +
            "(:prioridad IS NULL OR s.prioridad = :prioridad) AND " +
            "(:solicitanteId IS NULL OR s.solicitanteId = :solicitanteId)")
    Page<SolicitudEntity> buscarConFiltros(
            @Param("estado") String estado,
            @Param("prioridad") String prioridad,
            @Param("solicitanteId") String solicitanteId,
            Pageable pageable);

    @Query("SELECT COUNT(s) > 0 FROM SolicitudEntity s WHERE " +
            "s.solicitanteId = :solicitanteId AND " +
            "s.estado NOT IN ('CERRADA', 'ATENDIDA')")
    boolean existeSolicitudActivaPorSolicitante(
            @Param("solicitanteId") String solicitanteId);
}
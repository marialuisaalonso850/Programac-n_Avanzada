package co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.jpadata;

import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioJpaDataRepository extends JpaRepository<UsuarioEntity, String> {

    Optional<UsuarioEntity> findByEmail(String email);
    // Buscar por nombre exacto
    List<UsuarioEntity> findByNombre(String nombre);

    // Buscar usuarios por tipo
    List<UsuarioEntity> findByTipoUsuario(String tipoUsuario);

    // Buscar usuarios activos
    List<UsuarioEntity> findByEstado(String estado);

    // Combinado: Buscar por tipo Y estado
    List<UsuarioEntity> findByTipoUsuarioAndEstado(String tipo, String estado);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.email LIKE %:dominio%")
    List<UsuarioEntity> buscarPorDominioEmail(@Param("dominio") String dominio);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.tipoUsuario = :tipo AND u.estado = 'ACTIVO'")
    List<UsuarioEntity> obtenerUsuariosActivosPorTipo(@Param("tipo") String tipo);

    List<UsuarioEntity> findByNombreContainingIgnoreCase(String nombre);
    Page<UsuarioEntity> findByEstado(String estado, Pageable pageable);

}

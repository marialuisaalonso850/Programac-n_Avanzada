package co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.jpadata;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioJpaDataRepository extends JpaRepository<UsuarioEntity, String> {

    Optional<UsuarioEntity> findByEmail(String email);
}

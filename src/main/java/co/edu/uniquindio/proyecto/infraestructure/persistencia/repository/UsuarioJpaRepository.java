package co.edu.uniquindio.proyecto.infraestructure.persistencia.repository;

import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, String> {
    Optional<UsuarioEntity> findByEmail(String email);
}
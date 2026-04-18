package co.edu.uniquindio.proyecto.infraestructure.persistencia;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.entity.UsuarioEntity;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.UsuarioJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioJpaRepositoryTest {

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Test
    void debeGuardarYBuscarUsuario() {
        // GIVEN
        UsuarioEntity entity = UsuarioEntity.builder()
                .identificacion("1094123")
                .tipoDocumento("CEDULA_CIUDADANIA")
                .nombre("Prueba Persistencia")
                .email("test@persistencia.com")
                .tipoUsuario("ESTUDIANTE")
                .estado("ACTIVO")
                .build();

        // WHEN
        UsuarioEntity guardado = usuarioJpaRepository.save(entity);

        // THEN
        var encontrado = usuarioJpaRepository.findById("1094123");
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmail()).isEqualTo("test@persistencia.com");
    }
}
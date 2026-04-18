package co.edu.uniquindio.proyecto.infraestructure.persistencia;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoUsuario;
import co.edu.uniquindio.proyecto.infraestructure.persistencia.repository.adaptador.UsuarioJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class UsuarioJpaRepositoryTest {

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Test
    void debeGuardarYBuscarUsuario() {
        // GIVEN - Creamos un objeto de DOMINIO (porque el adaptador recibe Dominio)
        Usuario usuario = Usuario.crear(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1094123"),
                "Prueba Persistencia",
                new Email("test@persistencia.com"),
                TipoUsuario.ESTUDIANTE
        );

        // WHEN - Usamos el método definido en tu interfaz UsuarioRepository
        usuarioJpaRepository.crearUsuario(usuario);

        // THEN - Buscamos usando el Value Object de identificación
        Optional<Usuario> encontrado = usuarioJpaRepository.obtenerPorIdentificacion(
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1094123")
        );

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmail().valor()).isEqualTo("test@persistencia.com");
        assertThat(encontrado.get().getNombre()).isEqualTo("Prueba Persistencia");
    }
}
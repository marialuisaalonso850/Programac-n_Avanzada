package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el servicio {@link ClasificarSolicitudService}.
 *
 * <p>Verifica las reglas de negocio relacionadas con la clasificación
 * de una solicitud dentro del sistema.</p>
 *
 * <ul>
 *   <li>Solo usuarios con rol ADMIN o COORDINADOR pueden clasificar solicitudes.</li>
 * </ul>
 */
class ClasificarSolicitudServiceTest {

    private ClasificarSolicitudService service;
    private Solicitud solicitud;
    private Usuario usuario;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     * Se utilizan mocks para simular el comportamiento de las entidades.
     */
    @BeforeEach
    void setUp() {
        service = new ClasificarSolicitudService();
        solicitud = mock(Solicitud.class);
        usuario = mock(Usuario.class);
    }

    /**
     * Verifica que un usuario con rol ADMIN puede clasificar
     * correctamente una solicitud.
     */
    @Test
    void clasificar_admin_debeFuncionAR() {

        when(usuario.esAdmin()).thenReturn(true);

        service.ejecutar(solicitud, mock(TipoSolicitud.class), usuario, "obs");

        verify(solicitud).clasificar(any(), eq(usuario), eq("obs"));
    }

    /**
     * Verifica que si el usuario no es ADMIN ni COORDINADOR,
     * se lanza una excepción de dominio.
     */
    @Test
    void clasificar_noAutorizado_lanzaExcepcion() {

        when(usuario.esAdmin()).thenReturn(false);
        when(usuario.esCoordinador()).thenReturn(false);

        assertThrows(ReglaDominioException.class, () ->
                service.ejecutar(solicitud, mock(TipoSolicitud.class), usuario, "obs")
        );
    }
}
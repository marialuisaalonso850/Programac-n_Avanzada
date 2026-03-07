package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el servicio {@link CancelarSolicitudService}.
 *
 * <p>Verifica las reglas de negocio relacionadas con la cancelación de una solicitud.</p>
 *
 * <ul>
 *   <li>Un usuario puede cancelar su propia solicitud.</li>
 *   <li>Un usuario no autorizado no puede cancelar solicitudes de otros.</li>
 * </ul>
 */
class CancelarSolicitudServiceTest {

    private CancelarSolicitudService service;
    private Solicitud solicitud;
    private Usuario usuario;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     * Se utilizan mocks para simular el comportamiento de las entidades.
     */
    @BeforeEach
    void setUp() {
        service = new CancelarSolicitudService();
        solicitud = mock(Solicitud.class);
        usuario = mock(Usuario.class);
    }

    /**
     * Verifica que el usuario propietario de la solicitud
     * puede cancelarla correctamente.
     */
    @Test
    void cancelar_usuarioPropietario_debeFuncionAR() {

        when(solicitud.getUsuario()).thenReturn(usuario);

        service.ejecutar(solicitud, usuario, "obs");

        verify(solicitud).cancelar("obs");
    }

    /**
     * Verifica que si el usuario no es el propietario de la solicitud
     * ni tiene permisos administrativos, se lanza una excepción.
     */
    @Test
    void cancelar_noAutorizado_lanzaExcepcion() {

        when(usuario.esAdmin()).thenReturn(false);
        when(solicitud.getUsuario()).thenReturn(mock(Usuario.class));

        assertThrows(ReglaDominioException.class, () ->
                service.ejecutar(solicitud, usuario, "obs")
        );
    }
}
package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el servicio {@link CambiarPrioridadService}.
 *
 * <p>Verifica la regla de negocio relacionada con el cambio de prioridad
 * de una solicitud.</p>
 *
 * <p>Solo los usuarios autorizados pueden cambiar la prioridad.</p>
 *
 *  * * @author Maria Luisa Alonso
 *  *  * @author Valentina Orlas Pachon
 *  *  * @version 1.0
 *
 */
class CambiarPrioridadServiceTest {

    private CambiarPrioridadService service;
    private Solicitud solicitud;
    private Usuario usuario;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     * Se utilizan mocks para simular el comportamiento de las entidades.
     */
    @BeforeEach
    void setUp() {
        service = new CambiarPrioridadService();
        solicitud = mock(Solicitud.class);
        usuario = mock(Usuario.class);
    }

    /**
     * Verifica que un usuario autorizado puede cambiar la prioridad
     * de una solicitud correctamente.
     */
    @Test
    void cambiarPrioridad_autorizado_debeFuncionAR() {

        when(usuario.puedeCambiarPrioridad()).thenReturn(true);

        service.ejecutar(
                solicitud,
                Prioridad.ALTA,
                "justificación",
                usuario
        );

        verify(solicitud).cambiarPrioridad(
                Prioridad.ALTA,
                "justificación",
                usuario
        );
    }

    /**
     * Verifica que si el usuario no tiene permisos para cambiar la prioridad,
     * se lanza una excepción de dominio.
     */
    @Test
    void cambiarPrioridad_noAutorizado_lanzaExcepcion() {

        when(usuario.puedeCambiarPrioridad()).thenReturn(false);

        assertThrows(ReglaDominioException.class, () ->
                service.ejecutar(
                        solicitud,
                        Prioridad.ALTA,
                        "justificación",
                        usuario
                )
        );
    }
}
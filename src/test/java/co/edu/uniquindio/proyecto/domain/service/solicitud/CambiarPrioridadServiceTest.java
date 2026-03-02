package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CambiarPrioridadServiceTest {

    private CambiarPrioridadService service;
    private Solicitud solicitud;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        service = new CambiarPrioridadService();
        solicitud = mock(Solicitud.class);
        usuario = mock(Usuario.class);
    }

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

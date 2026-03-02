package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClasificarSolicitudServiceTest {

    private ClasificarSolicitudService service;
    private Solicitud solicitud;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        service = new ClasificarSolicitudService();
        solicitud = mock(Solicitud.class);
        usuario = mock(Usuario.class);
    }

    @Test
    void clasificar_admin_debeFuncionAR() {

        when(usuario.esAdmin()).thenReturn(true);

        service.ejecutar(solicitud, mock(TipoSolicitud.class), usuario, "obs");

        verify(solicitud).clasificar(any(), eq(usuario), eq("obs"));
    }

    @Test
    void clasificar_noAutorizado_lanzaExcepcion() {

        when(usuario.esAdmin()).thenReturn(false);
        when(usuario.esCoordinador()).thenReturn(false);

        assertThrows(ReglaDominioException.class, () ->
                service.ejecutar(solicitud, mock(TipoSolicitud.class), usuario, "obs")
        );
    }
}
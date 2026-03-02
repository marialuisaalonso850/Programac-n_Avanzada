package co.edu.uniquindio.proyecto.domain.service.solicitud;


import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CancelarSolicitudServiceTest {

    private CancelarSolicitudService service;
    private Solicitud solicitud;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        service = new CancelarSolicitudService();
        solicitud = mock(Solicitud.class);
        usuario = mock(Usuario.class);
    }

    @Test
    void cancelar_usuarioPropietario_debeFuncionAR() {

        when(solicitud.getUsuario()).thenReturn(usuario);

        service.ejecutar(solicitud, usuario, "obs");

        verify(solicitud).cancelar("obs");
    }

    @Test
    void cancelar_noAutorizado_lanzaExcepcion() {

        when(usuario.esAdmin()).thenReturn(false);
        when(solicitud.getUsuario()).thenReturn(mock(Usuario.class));

        assertThrows(ReglaDominioException.class, () ->
                service.ejecutar(solicitud, usuario, "obs")
        );
    }
}
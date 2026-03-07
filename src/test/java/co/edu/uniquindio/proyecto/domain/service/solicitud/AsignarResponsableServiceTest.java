package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el servicio {@link AsignarResponsableService}.
 *
 * <p>Verifica el cumplimiento de las reglas de negocio relacionadas con la
 * asignación de un responsable a una solicitud.</p>
 *
 *
 * * @author Maria Luisa Alonso
 *  * @author Valentina Orlas Pachon
 *  * @version 1.0
 */
class AsignarResponsableServiceTest {

    private AsignarResponsableService service;
    private Solicitud solicitud;
    private Usuario usuarioActual;
    private Usuario responsable;

    /**
     * Inicializa los objetos necesarios antes de cada prueba.
     * Se utilizan mocks para simular el comportamiento de las entidades.
     */
    @BeforeEach
    void setUp() {
        service = new AsignarResponsableService();
        solicitud = mock(Solicitud.class);
        usuarioActual = mock(Usuario.class);
        responsable = mock(Usuario.class);
    }

    /**
     * Verifica que un usuario con rol ADMIN puede asignar un responsable
     * a una solicitud correctamente.
     */
    @Test
    void asignar_adminAutorizado_debeFuncionar() {

        when(usuarioActual.esAdmin()).thenReturn(true);
        when(responsable.puedeSerResponsable()).thenReturn(true);

        service.ejecutar(solicitud, usuarioActual, responsable);

        verify(solicitud).asignarResponsable(responsable);
    }

    /**
     * Verifica que un usuario con rol COORDINADOR puede asignar
     * un responsable a una solicitud.
     */
    @Test
    void asignar_coordinadorAutorizado_debeFuncionar() {

        when(usuarioActual.esAdmin()).thenReturn(false);
        when(usuarioActual.esCoordinador()).thenReturn(true);
        when(responsable.puedeSerResponsable()).thenReturn(true);

        service.ejecutar(solicitud, usuarioActual, responsable);

        verify(solicitud).asignarResponsable(responsable);
    }

    /**
     * Verifica que si el usuario actual no es ADMIN ni COORDINADOR,
     * se lance una excepción y no se asigne responsable.
     */
    @Test
    void asignar_noAutorizado_lanzaExcepcion() {

        when(usuarioActual.esAdmin()).thenReturn(false);
        when(usuarioActual.esCoordinador()).thenReturn(false);

        assertThrows(ReglaDominioException.class, () ->
                service.ejecutar(solicitud, usuarioActual, responsable));

        verify(solicitud, never()).asignarResponsable(any());
    }

    /**
     * Verifica que si el usuario seleccionado no puede ser responsable,
     * se lance una excepción.
     */
    @Test
    void asignar_responsableNoPuedeSerlo_lanzaExcepcion() {

        when(usuarioActual.esAdmin()).thenReturn(true);
        when(responsable.puedeSerResponsable()).thenReturn(false);

        assertThrows(ReglaDominioException.class, () ->
                service.ejecutar(solicitud, usuarioActual, responsable));

        verify(solicitud, never()).asignarResponsable(any());
    }
}
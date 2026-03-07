package co.edu.uniquindio.proyecto.domain.service.solicitud;

import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsignarPrioridadServiceTest {

    private AsignarPrioridadService service;

    @BeforeEach
    void setUp() {
        service = new AsignarPrioridadService();
    }

    // ─── RF-03: Prioridad según tipo ──────────────────────────────────────────

    @Test
    void reingreso_debeAsignarPrioridadAlta() {
        // Arrange & Act
        Prioridad prioridad = service.calcular(TipoSolicitud.REINGRESO);

        // Assert
        assertEquals(Prioridad.ALTA, prioridad);
    }

    @Test
    void homologacion_debeAsignarPrioridadMedia() {
        // Arrange & Act
        Prioridad prioridad = service.calcular(TipoSolicitud.HOMOLOGACION);

        // Assert
        assertEquals(Prioridad.MEDIA, prioridad);
    }

    @Test
    void reembolsos_debeAsignarPrioridadMedia() {
        // Arrange & Act
        Prioridad prioridad = service.calcular(TipoSolicitud.REEMBOLSOS);

        // Assert
        assertEquals(Prioridad.MEDIA, prioridad);
    }

    @Test
    void cancelaciones_debeAsignarPrioridadBaja() {
        // Arrange & Act
        Prioridad prioridad = service.calcular(TipoSolicitud.CANCELACIONES);

        // Assert
        assertEquals(Prioridad.BAJA, prioridad);
    }

    @Test
    void tipoNulo_debeLanzarExcepcion() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                service.calcular(null));
    }
}
package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.TestData.UsuarioTestDataFactory;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void deberiaCrearSolicitudCorrectamente() {
        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();

        // Corregido: El constructor de Solicitud NO recibe TipoSolicitud (según tu código anterior)
        // Se inicializa en null hasta que se clasifica.
        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Necesito homologación"),
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        assertNotNull(solicitud.getId());
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getEstado());
        assertEquals(1, solicitud.getHistorial().size()); // Verifica que se registró el evento
    }

    @Test
    void deberiaAsignarResponsable() {
        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();
        Usuario coordinador = UsuarioTestDataFactory.crearCoordinadorValido();
        Usuario docente = UsuarioTestDataFactory.crearDocenteValido();

        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Prueba"),
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        // Paso 1: Clasificar (Obligatorio para poder asignar según tu validación de estado)
        solicitud.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, coordinador, "Ok");

        // Paso 2: Asignar Responsable
        // Corregido: Enviamos (Responsable, QuienAsigna)
        solicitud.asignarResponsable(docente, coordinador);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
        assertEquals(docente, solicitud.getResponsable());
    }

    @Test
    void deberiaCambiarPrioridad() {
        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();
        Usuario coordinador = UsuarioTestDataFactory.crearCoordinadorValido();
        Solicitud solicitud = new Solicitud(SolicitudId.generar(), new Descripcion("Prueba"), usuario, Prioridad.MEDIA, CanalOrigen.CSU);

        solicitud.cambiarPrioridad(Prioridad.ALTA, coordinador, "Se requiere urgencia");

        assertEquals(Prioridad.ALTA, solicitud.getPrioridad());
        // El historial ahora debe tener 2 eventos (Creación y Cambio de Prioridad)
        assertEquals(2, solicitud.getHistorial().size());
    }

    @Test
    void deberiaCancelarSolicitud() {
        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();
        Solicitud solicitud = new Solicitud(SolicitudId.generar(), new Descripcion("Prueba"), usuario, Prioridad.MEDIA, CanalOrigen.CSU);

        solicitud.cancelar(usuario, "Ya no lo necesito");

        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }
}
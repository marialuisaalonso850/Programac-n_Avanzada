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

        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Solicitud de homologación de materias"), // +10 caracteres
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        assertNotNull(solicitud.getId());
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getEstado());
        assertEquals(1, solicitud.getHistorial().size());
    }

    @Test
    void deberiaAsignarResponsable() {
        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();
        Usuario coordinador = UsuarioTestDataFactory.crearCoordinadorValido();
        Usuario docente = UsuarioTestDataFactory.crearDocenteValido();

        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Descripción válida de más de diez caracteres"), // Corregido
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        // Paso 1: Clasificar
        solicitud.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, coordinador, "Clasificación correcta");

        // Paso 2: Asignar Responsable
        solicitud.asignarResponsable(docente, coordinador);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
        assertEquals(docente, solicitud.getResponsable());
    }

    @Test
    void deberiaCambiarPrioridad() {
        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();
        Usuario coordinador = UsuarioTestDataFactory.crearCoordinadorValido();

        // Corregido: "Prueba" -> "Cambio de prioridad por urgencia"
        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Cambio de prioridad por urgencia médica"),
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        solicitud.cambiarPrioridad(Prioridad.ALTA, coordinador, "Se requiere urgencia");

        assertEquals(Prioridad.ALTA, solicitud.getPrioridad());
        assertEquals(2, solicitud.getHistorial().size());
    }

    @Test
    void deberiaCancelarSolicitud() {
        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();

        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Solicitud de retiro de asignatura por cruce"),
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        solicitud.cancelar(usuario, "Ya no lo necesito");

        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }
}
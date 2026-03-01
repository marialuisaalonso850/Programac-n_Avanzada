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
                new Descripcion("Necesito homologación"),
                TipoSolicitud.HOMOLOGACION,
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getEstado());
    }

    @Test
    void noDebePermitirDescripcionNula() {

        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();

        assertThrows(ReglaDominioException.class, () -> {
            new Solicitud(
                    SolicitudId.generar(),
                    null,
                    TipoSolicitud.HOMOLOGACION,
                    usuario,
                    Prioridad.MEDIA,
                    CanalOrigen.CSU
            );
        });
    }

    @Test
    void deberiaClasificarSolicitud() {

        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();
        Usuario coordinador = UsuarioTestDataFactory.crearCoordinadorValido();

        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Prueba"),
                TipoSolicitud.HOMOLOGACION,
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        solicitud.clasificar(
                TipoSolicitud.REINGRESO,
                coordinador,
                "Clasificación correcta"
        );

        assertEquals(EstadoSolicitud.CLASIFICADA, solicitud.getEstado());
    }

    @Test
    void deberiaAsignarResponsable() {

        Usuario usuario = UsuarioTestDataFactory.crearUsuarioValido();
        Usuario coordinador = UsuarioTestDataFactory.crearCoordinadorValido();
        Usuario docente = UsuarioTestDataFactory.crearDocenteValido();

        Solicitud solicitud = new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Prueba"),
                TipoSolicitud.HOMOLOGACION,
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );

        solicitud.clasificar(TipoSolicitud.REINGRESO, coordinador, "Ok");
        solicitud.asignarResponsable(docente);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }
}
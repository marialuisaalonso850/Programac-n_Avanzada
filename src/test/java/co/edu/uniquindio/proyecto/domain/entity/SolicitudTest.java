package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.TestData.UsuarioTestDataFactory;
import co.edu.uniquindio.proyecto.domain.exception.EstadoInvalidoException;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad Solicitud.
 * Verifica reglas de negocio y transiciones de estado.
 *
 * @author Maria Luisa Alonso
 * @author Valentina Orlas Pachon
 * @version 1.0
 */
public class SolicitudTest {

    private Usuario usuario;
    private Usuario coordinador;
    private Usuario docente;
    private Usuario admin;

    /**
     * Inicializa los usuarios necesarios para las pruebas.
     */
    @BeforeEach
    void setUp() {
        usuario     = UsuarioTestDataFactory.crearUsuarioValido();
        coordinador = UsuarioTestDataFactory.crearCoordinadorValido();
        docente     = UsuarioTestDataFactory.crearDocenteValido();
        admin       = UsuarioTestDataFactory.crearAdminValido();
    }

    /**
     * Crea una solicitud en estado REGISTRADA.
     */
    private Solicitud crearSolicitudRegistrada() {
        return new Solicitud(
                SolicitudId.generar(),
                new Descripcion("Descripcion valida para test"),
                TipoSolicitud.HOMOLOGACION,
                usuario,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );
    }

    /**
     * Crea una solicitud en estado CLASIFICADA.
     */
    private Solicitud crearSolicitudClasificada() {
        Solicitud s = crearSolicitudRegistrada();
        s.clasificar(TipoSolicitud.REINGRESO, coordinador, "Clasificada ok");
        return s;
    }

    /**
     * Crea una solicitud en estado EN_ATENCION.
     */
    private Solicitud crearSolicitudEnAtencion() {
        Solicitud s = crearSolicitudClasificada();
        s.asignarResponsable(docente);
        return s;
    }

    /**
     * Crea una solicitud en estado ATENDIDA.
     */
    private Solicitud crearSolicitudAtendida() {
        Solicitud s = crearSolicitudEnAtencion();
        s.marcarComoAtendida("Atendida correctamente");
        return s;
    }

    /**
     * Verifica que una solicitud se cree correctamente.
     */
    @Test
    void deberiaCrearSolicitudCorrectamente() {
        Solicitud solicitud = crearSolicitudRegistrada();
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getEstado());
    }

    /**
     * No se debe permitir crear una solicitud con id nulo.
     */
    @Test
    void noDebePermitirIdNulo() {
        assertThrows(ReglaDominioException.class, () ->
                new Solicitud(null,
                        new Descripcion("Descripcion valida para test"),
                        TipoSolicitud.HOMOLOGACION,
                        usuario, Prioridad.MEDIA, CanalOrigen.CSU));
    }

    /**
     * No se debe permitir una descripción nula.
     */
    @Test
    void noDebePermitirDescripcionNula() {
        assertThrows(ReglaDominioException.class, () ->
                new Solicitud(SolicitudId.generar(),
                        null,
                        TipoSolicitud.HOMOLOGACION,
                        usuario, Prioridad.MEDIA, CanalOrigen.CSU));
    }

    /**
     * No se debe permitir un tipo de solicitud nulo.
     */
    @Test
    void noDebePermitirTipoNulo() {
        assertThrows(ReglaDominioException.class, () ->
                new Solicitud(SolicitudId.generar(),
                        new Descripcion("Descripcion valida para test"),
                        null,
                        usuario, Prioridad.MEDIA, CanalOrigen.CSU));
    }

    /**
     * No se debe permitir un usuario nulo.
     */
    @Test
    void noDebePermitirUsuarioNulo() {
        assertThrows(ReglaDominioException.class, () ->
                new Solicitud(SolicitudId.generar(),
                        new Descripcion("Descripcion valida para test"),
                        TipoSolicitud.HOMOLOGACION,
                        null, Prioridad.MEDIA, CanalOrigen.CSU));
    }

    /**
     * No se debe permitir una prioridad nula.
     */
    @Test
    void noDebePermitirPrioridadNula() {
        assertThrows(ReglaDominioException.class, () ->
                new Solicitud(SolicitudId.generar(),
                        new Descripcion("Descripcion valida para test"),
                        TipoSolicitud.HOMOLOGACION,
                        usuario, null, CanalOrigen.CSU));
    }

    /**
     * No se debe permitir un canal de origen nulo.
     */
    @Test
    void noDebePermitirCanalNulo() {
        assertThrows(ReglaDominioException.class, () ->
                new Solicitud(SolicitudId.generar(),
                        new Descripcion("Descripcion valida para test"),
                        TipoSolicitud.HOMOLOGACION,
                        usuario, Prioridad.MEDIA, null));
    }

    /**
     * Verifica que el estado inicial sea REGISTRADA.
     */
    @Test
    void estadoInicialDebeSerRegistrada() {
        Solicitud solicitud = crearSolicitudRegistrada();
        assertEquals(EstadoSolicitud.REGISTRADA, solicitud.getEstado());
    }

    /**
     * Verifica que se registre un evento en el historial al crear la solicitud.
     */
    @Test
    void debeRegistrarEventoAlCrearSolicitud() {
        Solicitud solicitud = crearSolicitudRegistrada();

        assertEquals(1, solicitud.getHistorial().size());
        assertEquals(TipoAccion.SOLICITUD_REGISTRADA,
                solicitud.getHistorial().get(0).accion());
    }

    /**
     * Verifica que una solicitud pueda ser clasificada.
     */
    @Test
    void deberiaClasificarSolicitud() {
        Solicitud solicitud = crearSolicitudRegistrada();

        solicitud.clasificar(TipoSolicitud.REINGRESO, coordinador, "Clasificacion ok");

        assertEquals(EstadoSolicitud.CLASIFICADA, solicitud.getEstado());
    }

    /**
     * No se puede clasificar una solicitud que no esté registrada.
     */
    @Test
    void noDebeClasificarSiNoEstaRegistrada() {
        Solicitud solicitud = crearSolicitudClasificada();

        assertThrows(EstadoInvalidoException.class, () ->
                solicitud.clasificar(TipoSolicitud.REINGRESO, coordinador, "obs"));
    }

    /**
     * No se puede clasificar sin observación.
     */
    @Test
    void noDebeClasificarSinObservacion() {
        Solicitud solicitud = crearSolicitudRegistrada();

        assertThrows(ReglaDominioException.class, () ->
                solicitud.clasificar(TipoSolicitud.REINGRESO, coordinador, ""));
    }

    /**
     * Verifica que se pueda asignar un responsable.
     */
    @Test
    void deberiaAsignarResponsable() {
        Solicitud solicitud = crearSolicitudClasificada();

        solicitud.asignarResponsable(docente);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
    }

    /**
     * No se puede asignar responsable si no está clasificada.
     */
    @Test
    void noDebeAsignarResponsableSiNoEstaClasificada() {
        Solicitud solicitud = crearSolicitudRegistrada();

        assertThrows(EstadoInvalidoException.class, () ->
                solicitud.asignarResponsable(docente));
    }

    /**
     * No se puede asignar responsable nulo.
     */
    @Test
    void noDebeAsignarResponsableNulo() {
        Solicitud solicitud = crearSolicitudClasificada();

        assertThrows(ReglaDominioException.class, () ->
                solicitud.asignarResponsable(null));
    }

    /**
     * No se puede asignar un responsable inactivo.
     */
    @Test
    void noDebeAsignarResponsableInactivo() {
        Solicitud solicitud = crearSolicitudClasificada();
        docente.desactivar();

        assertThrows(ReglaDominioException.class, () ->
                solicitud.asignarResponsable(docente));
    }

    /**
     * Verifica que una solicitud pueda marcarse como atendida.
     */
    @Test
    void deberiaMarcarComoAtendida() {
        Solicitud solicitud = crearSolicitudEnAtencion();

        solicitud.marcarComoAtendida("Solicitud resuelta");

        assertEquals(EstadoSolicitud.ATENDIDA, solicitud.getEstado());
    }

    /**
     * No se puede marcar como atendida si no está en atención.
     */
    @Test
    void noDebeMarcarAtendidaSiNoEstaEnAtencion() {

        Solicitud solicitud = crearSolicitudClasificada();

        assertThrows(EstadoInvalidoException.class, () ->
                solicitud.marcarComoAtendida("obs"));
    }

    /**
     * No se puede marcar como atendida sin observación.
     */
    @Test
    void noDebeMarcarAtendidaSinObservacion() {
        Solicitud solicitud = crearSolicitudEnAtencion();

        assertThrows(ReglaDominioException.class, () ->
                solicitud.marcarComoAtendida(""));
    }

    /**
     * Verifica que una solicitud pueda cerrarse.
     */
    @Test
    void deberiaCerrarSolicitud() {
        Solicitud solicitud = crearSolicitudAtendida();

        solicitud.cerrar("Cierre formal");

        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    /**
     * Verifica que el historial sea inmutable.
     */
    @Test
    void historialDebeSerInmutable() {
        Solicitud solicitud = crearSolicitudRegistrada();

        List<Historial> historial = solicitud.getHistorial();
        assertThrows(UnsupportedOperationException.class, () ->
                historial.add(null));
    }
}
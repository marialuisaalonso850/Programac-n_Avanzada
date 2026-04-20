package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.exception.EstadoInvalidoException;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    private Usuario solicitante;
    private Usuario admin;
    private Usuario responsable;
    private Solicitud solicitud;

    @BeforeEach
    void setUp() {
        solicitante = Usuario.builder()
                .identificacion(new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "123"))
                .nombre("Juan Perez")
                .estado(EstadoUsuario.ACTIVO)
                .build();

        admin = Usuario.builder()
                .identificacion(new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "999"))
                .tipoUsuario(TipoUsuario.ADMIN)
                .estado(EstadoUsuario.ACTIVO)
                .build();

        // Si son activos por defecto, no llamamos a .estaActivo(true) para evitar errores de compilación
        responsable = Usuario.builder()
                .identificacion(new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1004798819"))
                .nombre("PRUEBA DOCENTE")
                .estado(EstadoUsuario.ACTIVO)
                .build();

        solicitud = new Solicitud(
                new SolicitudId(UUID.randomUUID()),
                new Descripcion("Descripcion de prueba"),
                solicitante,
                Prioridad.MEDIA,
                CanalOrigen.CSU
        );
    }

    @Test
    void validarClasificacionYCambioPrioridad() {
        solicitud.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, Prioridad.ALTA, admin, "Clasificación exitosa");

        assertEquals(EstadoSolicitud.CLASIFICADA, solicitud.getEstado());
        assertEquals(Prioridad.ALTA, solicitud.getPrioridad());
        assertNotNull(solicitud.getTipo());
    }

    @Test
    void validarAsignacionResponsable() {
        solicitud.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, Prioridad.ALTA, admin, "OK");

        solicitud.asignarResponsable(responsable, admin);

        assertEquals(EstadoSolicitud.EN_ATENCION, solicitud.getEstado());
        assertNotNull(solicitud.getResponsable());
        assertEquals("1004798819", solicitud.getResponsable().getIdentificacion().numero());
    }

    @Test
    void errorAlAsignarSinClasificar() {
        assertThrows(EstadoInvalidoException.class, () ->
                solicitud.asignarResponsable(responsable, admin)
        );
    }

    @Test
    void validarFlujoHastaCierre() {
        solicitud.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, Prioridad.ALTA, admin, "OK");
        solicitud.asignarResponsable(responsable, admin);
        solicitud.marcarComoAtendida("Atendida");
        solicitud.cerrar("Finalizado");

        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    @Test
    void validarRegistroEnHistorial() {
        int eventosIniciales = solicitud.getHistorial().size();
        solicitud.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, Prioridad.BAJA, admin, "Nota");

        assertEquals(eventosIniciales + 1, solicitud.getHistorial().size());
    }

    @Test
    void errorAlCerrarSinAtender() {
        solicitud.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, Prioridad.MEDIA, admin, "OK");

        assertThrows(EstadoInvalidoException.class, () ->
                solicitud.cerrar("Cierre directo")
        );
    }
}
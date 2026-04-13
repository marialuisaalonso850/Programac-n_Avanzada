package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.domain.exception.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Solicitud {

    private SolicitudId id;
    private LocalDateTime fechaCreacion;
    private Usuario usuario; // El solicitante
    private Descripcion descripcion;
    private Usuario responsable; // Inicia en null
    private TipoSolicitud tipo;   // Inicia en null
    private EstadoSolicitud estado;
    private Prioridad prioridad;
    private CanalOrigen canal;
    private List<Historial> historial;

    /**
     * Constructor para Registro de Solicitudes (RF-01)
     * Cumple con la Guía: Al registrarse no tiene responsable ni tipo definitivo.
     */
    public Solicitud(SolicitudId id, Descripcion descripcion,
                     Usuario usuario, Prioridad prioridad, CanalOrigen canal) {

        validarObligatorios(id, descripcion, usuario, prioridad, canal);

        this.id = id;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.prioridad = prioridad;
        this.canal = canal;

        // Inicialización de valores por defecto (RF-01, RF-04)
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoSolicitud.REGISTRADA;
        this.historial = new ArrayList<>();

        // Estos campos permanecen null hasta su gestión (Clasificación/Asignación)
        this.tipo = null;
        this.responsable = null;

        registrarEvento(TipoAccion.SOLICITUD_REGISTRADA, usuario,
                "Solicitud registrada exitosamente vía " + canal.name());
    }

    // --- MÉTODOS DE COMPORTAMIENTO (LOGICA DE DOMINIO) ---

    /**
     * RF-02: Clasificación de solicitudes académicas.
     */
    public void clasificar(TipoSolicitud nuevoTipo, Usuario funcionario, String observacion) {
        validarEstado(EstadoSolicitud.REGISTRADA, "Solo se puede clasificar si la solicitud está REGISTRADA");

        if (nuevoTipo == null) throw new ReglaDominioException("El tipo de solicitud es obligatorio.");

        this.tipo = nuevoTipo;
        this.estado = EstadoSolicitud.CLASIFICADA; // Transición coherente (RF-04)
        registrarEvento(TipoAccion.CLASIFICADA, funcionario, observacion);
    }

    /**
     * RF-05: Asignación de responsables autorizados y activos.
     */
    public void asignarResponsable(Usuario nuevoResponsable, Usuario gestor) {
        // Validación de flujo: Según RF-04 y RF-05, debe estar clasificada primero.
        validarEstado(EstadoSolicitud.CLASIFICADA, "Debe estar CLASIFICADA para poder asignar un responsable");

        if (nuevoResponsable == null || !nuevoResponsable.estaActivo()) {
            throw new ReglaDominioException("El responsable debe ser un usuario activo.");
        }

        this.responsable = nuevoResponsable;
        this.estado = EstadoSolicitud.EN_ATENCION; // Transición coherente (RF-04)
        registrarEvento(TipoAccion.RESPONSABLE_ASIGNADO, gestor, "Responsable asignado: " + nuevoResponsable.getNombre());
    }

    /**
     * Marcado de atención.
     */
    public void marcarComoAtendida(String observacion) {
        validarEstado(EstadoSolicitud.EN_ATENCION, "Debe estar EN_ATENCION para marcarse como atendida");

        this.estado = EstadoSolicitud.ATENDIDA;
        registrarEvento(TipoAccion.ESTADO_MODIFICADO, this.responsable, observacion);
    }

    /**
     * RF-08: Cierre de solicitudes (Únicamente si atendida y con observación).
     */
    public void cerrar(String observacion) {
        validarEstado(EstadoSolicitud.ATENDIDA, "Solo se puede cerrar si ya fue ATENDIDA");

        if (observacion == null || observacion.isBlank()) {
            throw new ReglaDominioException("Es obligatorio registrar una observación de cierre.");
        }

        this.estado = EstadoSolicitud.CERRADA;
        registrarEvento(TipoAccion.SOLICITUD_CERRADA, this.responsable, observacion);
    }

    // --- MÉTODOS DE APOYO (ENCAPSULAMIENTO) ---

    /**
     * RF-06: Registro del historial auditable.
     */
    private void registrarEvento(TipoAccion accion, Usuario actor, String obs) {
        this.historial.add(new Historial(
                LocalDateTime.now(),
                accion,
                actor.getIdentificacion(),
                obs != null ? obs : "Sin observación"
        ));
    }

    private void validarEstado(EstadoSolicitud esperado, String mensaje) {
        if (this.estado != esperado) {
            throw new EstadoInvalidoException(mensaje + ". Estado actual: " + this.estado);
        }
    }

    private void validarObligatorios(Object... objetos) {
        for (Object obj : objetos) {
            if (obj == null) throw new ReglaDominioException("Faltan datos obligatorios para la solicitud");
        }
    }

    public void cambiarPrioridad(Prioridad nuevaPrioridad, Usuario funcionario, String observacion) {
        if (nuevaPrioridad == null) throw new ReglaDominioException("La nueva prioridad no puede ser nula.");

        Prioridad anterior = this.prioridad;
        this.prioridad = nuevaPrioridad;

        registrarEvento(TipoAccion.ESTADO_MODIFICADO, funcionario,
                "Cambio de prioridad de " + anterior + " a " + nuevaPrioridad + ". Obs: " + observacion);
    }

    public void cancelar(Usuario actor, String motivo) {
        // Validación: No se puede cancelar algo que ya terminó su proceso
        if (this.estado == EstadoSolicitud.CERRADA || this.estado == EstadoSolicitud.ATENDIDA) {
            throw new ReglaDominioException("No se puede cancelar una solicitud que ya está finalizada.");
        }

        if (motivo == null || motivo.isBlank()) {
            throw new ReglaDominioException("Debe proporcionar un motivo para la cancelación.");
        }

        this.estado = EstadoSolicitud.CANCELADA; // Asegúrate de tener CANCELADA en tu Enum EstadoSolicitud
        registrarEvento(TipoAccion.SOLICITUD_CANCELADA, actor, "Solicitud cancelada por el usuario. Motivo: " + motivo);
    }

    public List<Historial> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.exception.EstadoInvalidoException;
import co.edu.uniquindio.proyecto.domain.exception.SolicitudCerradaException;
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
    private Usuario usuario;
    private Descripcion descripcion;
    private Usuario responsable;
    private TipoSolicitud tipo;
    private EstadoSolicitud estado;
    private Prioridad prioridad;
    private CanalOrigen canal;
    private List<Historial> historial;

    // Constructor para nuevas solicitudes
    public Solicitud(SolicitudId id, Descripcion descripcion, TipoSolicitud tipo,
                     Usuario usuario, Prioridad prioridad, CanalOrigen canal) {

        validarObligatorios(id, descripcion, tipo, usuario, prioridad, canal);

        this.id = id;
        this.descripcion = descripcion;
        this.canal = canal;
        this.tipo = tipo;
        this.usuario = usuario;
        this.prioridad = prioridad;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoSolicitud.REGISTRADA;
        this.historial = new ArrayList<>();

        registrarEvento(TipoAccion.SOLICITUD_REGISTRADA, usuario,
                "Solicitud creada vía " + canal.name());
    }

    // --- MÉTODOS DE COMPORTAMIENTO (LOGICA DE DOMINIO) ---

    public void clasificar(TipoSolicitud nuevoTipo, Usuario funcionario, String obs) {
        validarEstado(EstadoSolicitud.REGISTRADA, "Solo se clasifica si está REGISTRADA");

        this.tipo = nuevoTipo;
        this.estado = EstadoSolicitud.CLASIFICADA;
        registrarEvento(TipoAccion.CLASIFICADA, funcionario, obs);
    }

    public void asignarResponsable(Usuario responsable) {
        validarEstado(EstadoSolicitud.CLASIFICADA, "Debe estar CLASIFICADA para asignar");

        if (responsable == null || !responsable.estaActivo())
            throw new ReglaDominioException("El responsable no es válido o no está activo");

        this.responsable = responsable;
        this.estado = EstadoSolicitud.EN_ATENCION;
        registrarEvento(TipoAccion.RESPONSABLE_ASIGNADO, responsable, "Responsable asignado");
    }

    public void marcarComoAtendida(String obs) {
        validarEstado(EstadoSolicitud.EN_ATENCION, "Debe estar EN_ATENCION para marcar como atendida");

        this.estado = EstadoSolicitud.ATENDIDA;
        registrarEvento(TipoAccion.SOLICITUD_ATENDIDA, this.responsable, obs);
    }

    public void cerrar(String obs) {
        validarEstado(EstadoSolicitud.ATENDIDA, "Solo se cierra si ya fue ATENDIDA");

        this.estado = EstadoSolicitud.CERRADA;
        registrarEvento(TipoAccion.SOLICITUD_CERRADA, this.responsable, obs);
    }

    public void cancelar(String obs, Usuario actor) {
        if (this.estado == EstadoSolicitud.CERRADA)
            throw new SolicitudCerradaException("No se puede cancelar una solicitud cerrada");

        this.estado = EstadoSolicitud.CANCELADA;
        registrarEvento(TipoAccion.SOLICITUD_CANCELADA, actor, obs);
    }

    public void cambiarPrioridad(Prioridad nueva, String justificacion, Usuario actor) {
        if (this.estado == EstadoSolicitud.CERRADA || this.estado == EstadoSolicitud.CANCELADA)
            throw new SolicitudCerradaException("No se puede modificar una solicitud finalizada");

        this.prioridad = nueva;
        registrarEvento(TipoAccion.PRIORIDAD_MODIFICADA, actor, "Motivo: " + justificacion);
    }


    private void registrarEvento(TipoAccion accion, Usuario usuario, String obs) {
        this.historial.add(new Historial(
                LocalDateTime.now(),
                accion,
                usuario.getIdentificacion(),
                obs != null ? obs : "Sin observación"
        ));
    }

    private void validarEstado(EstadoSolicitud esperado, String mensaje) {
        if (this.estado != esperado) throw new EstadoInvalidoException(mensaje);
    }

    private void validarObligatorios(Object... objetos) {
        for (Object obj : objetos) {
            if (obj == null) throw new ReglaDominioException("Faltan datos obligatorios");
        }
    }

    public List<Historial> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
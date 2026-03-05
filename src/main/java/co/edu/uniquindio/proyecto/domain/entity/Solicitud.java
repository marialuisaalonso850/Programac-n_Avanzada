package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;
import co.edu.uniquindio.proyecto.domain.exception.EstadoInvalidoException;
import co.edu.uniquindio.proyecto.domain.exception.SolicitudCerradaException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud {

    private  SolicitudId id;
    private  LocalDateTime fechaCreacion;
    private  Usuario usuario;
    private Descripcion descripcion;
    private Usuario responsable;
    private TipoSolicitud tipo;
    private EstadoSolicitud estado;
    private Prioridad prioridad;
    private CanalOrigen canal;
    private List<Historial> historial;

    public Solicitud(SolicitudId id,
                     Descripcion descripcion,
                     TipoSolicitud tipo,
                     Usuario usuario,
                     Prioridad prioridad,
                     CanalOrigen canal) {

        if (id == null)
            throw new ReglaDominioException("El id es obligatorio");

        if (descripcion == null )
            throw new ReglaDominioException("La descripción es obligatoria");

        if (tipo == null)
            throw new ReglaDominioException("El tipo es obligatorio");

        if (usuario == null)
            throw new ReglaDominioException("El usuario es obligatorio");

        if (prioridad == null)
            throw new ReglaDominioException("La prioridad es obligatoria");

        if (canal == null)
            throw new ReglaDominioException("El canal de origen es obligatorio");

        this.id = id;
        this.descripcion = descripcion;
        this.canal = canal;
        this.tipo = tipo;
        this.usuario = usuario;
        this.prioridad = prioridad;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoSolicitud.REGISTRADA;
        this.historial = new ArrayList<>();

        registrarEvento(
                TipoAccion.SOLICITUD_REGISTRADA,
                usuario,
                "Solicitud creada por canal: " + canal.name()
        );
    }


    public void clasificar(TipoSolicitud nuevoTipo, Usuario funcionario, String observacion) {

        if (this.estado != EstadoSolicitud.REGISTRADA)
            throw new EstadoInvalidoException(
                    "Solo solicitudes registradas pueden clasificarse"
            );

        if (nuevoTipo == null)
            throw new ReglaDominioException("El tipo es obligatorio");

        if (funcionario == null)
            throw new ReglaDominioException("El funcionario es obligatorio");

        if (observacion == null || observacion.isBlank())
            throw new ReglaDominioException("Debe registrar observación");

        this.tipo = nuevoTipo;
        this.estado = EstadoSolicitud.CLASIFICADA;

        registrarEvento(
                TipoAccion.CLASIFICADA,
                funcionario,
                "Tipo asignado: " + nuevoTipo.name() + ". " + observacion
        );
    }

    public void asignarResponsable(Usuario responsable) {

        if (this.estado != EstadoSolicitud.CLASIFICADA)
            throw new EstadoInvalidoException(
                    "Solo solicitudes clasificadas pueden asignarse"
            );

        if (responsable == null)
            throw new ReglaDominioException("El responsable es obligatorio");

        if (!responsable.estaActivo())
            throw new ReglaDominioException("El responsable debe estar activo");

        this.responsable = responsable;
        this.estado = EstadoSolicitud.EN_ATENCION;

        registrarEvento(TipoAccion.RESPONSABLE_ASIGNADO, responsable, "Se inicia atención");
    }

    public void marcarComoAtendida(String observacion) {

        if (this.estado != EstadoSolicitud.EN_ATENCION)
            throw new EstadoInvalidoException(
                    "Solo solicitudes en atención pueden marcarse como atendidas"
            );

        if (this.responsable == null)
            throw new ReglaDominioException(
                    "La solicitud no tiene responsable asignado"
            );

        if (observacion == null || observacion.isBlank())
            throw new ReglaDominioException("Debe registrar observación");

        this.estado = EstadoSolicitud.ATENDIDA;

        registrarEvento(TipoAccion.SOLICITUD_ATENDIDA, this.responsable, observacion);
    }

    public void cerrar(String observacion) {

        if (this.estado != EstadoSolicitud.ATENDIDA)
            throw new EstadoInvalidoException(
                    "Solo solicitudes atendidas pueden cerrarse"
            );

        if (this.responsable == null)
            throw new ReglaDominioException(
                    "La solicitud no tiene responsable asignado"
            );

        if (observacion == null || observacion.isBlank())
            throw new ReglaDominioException(
                    "Debe registrar observación de cierre"
            );

        this.estado = EstadoSolicitud.CERRADA;

        registrarEvento(TipoAccion.SOLICITUD_CERRADA, this.responsable, observacion);
    }

    public void cancelar(String observacion) {

        if (this.estado == EstadoSolicitud.CERRADA)
            throw new SolicitudCerradaException(
                    "No se puede cancelar una solicitud cerrada"
            );

        if (this.estado == EstadoSolicitud.CANCELADA)
            throw new EstadoInvalidoException(
                    "La solicitud ya está cancelada"
            );

        if (observacion == null || observacion.isBlank())
            throw new ReglaDominioException(
                    "Debe registrar observación de cancelación"
            );

        this.estado = EstadoSolicitud.CANCELADA;

        registrarEvento(
                TipoAccion.SOLICITUD_CANCELADA,
                this.usuario,
                observacion
        );
    }

    private boolean esTransicionValida(EstadoSolicitud actual, EstadoSolicitud nuevo) {

        return switch (actual) {
            case REGISTRADA -> nuevo == EstadoSolicitud.CLASIFICADA
                    || nuevo == EstadoSolicitud.CANCELADA;

            case CLASIFICADA -> nuevo == EstadoSolicitud.EN_ATENCION
                    || nuevo == EstadoSolicitud.CANCELADA;

            case EN_ATENCION -> nuevo == EstadoSolicitud.ATENDIDA
                    || nuevo == EstadoSolicitud.CANCELADA;

            case ATENDIDA -> nuevo == EstadoSolicitud.CERRADA
                    || nuevo == EstadoSolicitud.CANCELADA;

            case CANCELADA, CERRADA -> false;
        };
    }
    public void cambiarEstado(EstadoSolicitud nuevoEstado, String observacion, Usuario usuario) {

        if (this.estado == EstadoSolicitud.CERRADA)
            throw new SolicitudCerradaException(
                    "No se puede cambiar el estado de una solicitud cerrada"
            );

        if (usuario == null)
            throw new ReglaDominioException("El usuario que modifica es obligatorio");

        if (nuevoEstado == null)
            throw new ReglaDominioException("El nuevo estado es obligatorio");

        if (observacion == null || observacion.isBlank())
            throw new ReglaDominioException("Debe registrar observación");

        if (!esTransicionValida(this.estado, nuevoEstado)) {
            throw new EstadoInvalidoException(
                    "Transición de estado no permitida"
            );
        }

        this.estado = nuevoEstado;

        registrarEvento(
                TipoAccion.ESTADO_MODIFICADO,
                usuario,
                "Estado cambiado a: " + nuevoEstado.name() + ". " + observacion
        );
    }

    public void cambiarPrioridad(Prioridad nuevaPrioridad,String justificacion, Usuario usuarioModifica) {

        if (this.estado == EstadoSolicitud.CERRADA)
            throw new SolicitudCerradaException(
                    "No se puede modificar una solicitud cerrada"
            );

        if (this.estado == EstadoSolicitud.CANCELADA)
            throw new EstadoInvalidoException(
                    "No se puede modificar una solicitud cancelada"
            );

        if (nuevaPrioridad == null)
            throw new ReglaDominioException(
                    "La prioridad no puede ser nula"
            );

        if (justificacion == null || justificacion.isBlank())
            throw new ReglaDominioException("Debe justificar el cambio de prioridad");

        this.prioridad = nuevaPrioridad;

        registrarEvento(TipoAccion.PRIORIDAD_MODIFICADA, usuarioModifica,
                "Nueva prioridad: " + nuevaPrioridad.name());
    }


    private void registrarEvento(TipoAccion accion, Usuario usuario, String observacion) {

        historial.add(new Historial(
                LocalDateTime.now(),
                accion,
                usuario.getIdentificacion(),
                observacion
        ));
    }


    public List<Historial> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", estado=" + estado +
                ", prioridad=" + prioridad +
                ", tipo=" + tipo +
                ", usuario=" + usuario.getNombre() +
                '}';
    }
}
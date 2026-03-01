package co.edu.uniquindio.proyecto.domain.service;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public class SolicitudService {

    public void cancelarSolicitud(
            Solicitud solicitud,
            Usuario usuarioActual,
            String observacion
    ) {

        if (!usuarioActual.equals(solicitud.getUsuario())
                && !usuarioActual.esAdmin()) {
            throw new ReglaDominioException("No autorizado para cancelar");
        }

        solicitud.cancelar(observacion);
    }

    public void clasificar(
            Solicitud solicitud,
            TipoSolicitud nuevoTipo,
            Usuario usuarioActual,
            String observacion
    ) {

        if (!usuarioActual.esAdmin()
                && !usuarioActual.esCoordinador()) {

            throw new ReglaDominioException(
                    "Solo un administrador o coordinador puede clasificar"
            );
        }

        solicitud.clasificar(nuevoTipo, usuarioActual, observacion);
    }

    public void marcarComoAtendida(
            Solicitud solicitud,
            Usuario usuarioActual,
            String observacion
    ) {

        if (!usuarioActual.equals(solicitud.getResponsable())
                && !usuarioActual.esAdmin()) {

            throw new ReglaDominioException(
                    "Solo el responsable o admin puede marcar como atendida"
            );
        }

        solicitud.marcarComoAtendida(observacion);
    }

    public void cerrarSolicitud(
            Solicitud solicitud,
            Usuario usuarioActual,
            String observacion
    ) {

        if (!usuarioActual.equals(solicitud.getResponsable())
                && !usuarioActual.esAdmin())
            throw new ReglaDominioException("Solo el responsable puede cerrar la solicitud");

        solicitud.cerrar(observacion);
    }

    public void cambiarPrioridad(
            Solicitud solicitud,
            Prioridad nuevaPrioridad,
            String justificacion,
            Usuario usuarioActual
    ) {

        if (!usuarioActual.puedeCambiarPrioridad()) {
            throw new ReglaDominioException(
                    "Solo un administrador o coordinador puede cambiar la prioridad"
            );
        }

        solicitud.cambiarPrioridad(
                nuevaPrioridad,
                justificacion,
                usuarioActual
        );
    }


    public void asignarResponsable(
            Solicitud solicitud,
            Usuario usuarioActual,
            Usuario responsable
    ) {

        if (!usuarioActual.esAdmin() && !usuarioActual.esCoordinador()) {
            throw new ReglaDominioException(
                    "Solo un administrador o coordinador puede asignar responsable"
            );
        }

        if (!responsable.puedeSerResponsable()) {
            throw new ReglaDominioException(
                    "El usuario seleccionado no puede ser responsable"
            );
        }

        solicitud.asignarResponsable(responsable);
    }
}
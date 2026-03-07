package co.edu.uniquindio.proyecto.domain.service.solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Prioridad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;

public class AsignarPrioridadService {
    /**
     * Calcula la prioridad sugerida de una solicitud
     * según su tipo, basándose en el impacto académico.
     * RF-03 — Priorización de solicitudes.
     */
    public Prioridad calcular(TipoSolicitud tipo) {

        if (tipo == null)
            throw new IllegalArgumentException("El tipo de solicitud es obligatorio");

        return switch (tipo) {
            case REINGRESO              -> Prioridad.ALTA;
            case HOMOLOGACION           -> Prioridad.MEDIA;
            case REEMBOLSOS             -> Prioridad.MEDIA;
            case CANCELACIONES          -> Prioridad.BAJA;
            case REGISTRO_ASIGNATURAS   -> Prioridad.ALTA;
            case SOLICITUD_CUPOS        -> Prioridad.MEDIA;
            case CONSULTA_ACADEMICA     -> Prioridad.BAJA;
        };
    }
}

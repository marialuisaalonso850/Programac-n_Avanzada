package co.edu.uniquindio.proyecto.infraestructure.repository;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.SolicitudId;
import java.util.*;

public class SolicitudRepositoryEnMemoria implements SolicitudRepository {

    private final Map<SolicitudId, Solicitud> baseDatos = new HashMap<>();

    @Override
    public void guardar(Solicitud solicitud) {
        baseDatos.put(solicitud.getId(), solicitud);
    }

    @Override
    public Optional<Solicitud> obtenerPorId(SolicitudId id) {
        return Optional.ofNullable(baseDatos.get(id));
    }

    @Override
    public List<Solicitud> obtenerPorEstado(EstadoSolicitud estado) {
        return baseDatos.values()
                .stream()
                .filter(s -> s.getEstado() == estado)
                .toList();
    }
    @Override
    public List<Solicitud> obtenerTodas() {
        return new ArrayList<>(baseDatos.values());
    }

    @Override
    public List<Solicitud> obtenerPorUsuario(String usuarioId) {
        return baseDatos.values()
                .stream()
                .filter(s -> s.getUsuario().getIdentificacion().equals(usuarioId))
                .toList();
    }

    @Override
    public List<Solicitud> obtenerPorResponsable(String responsableId) {
        return baseDatos.values()
                .stream()
                .filter(s -> s.getResponsable() != null &&
                        s.getResponsable().getIdentificacion().equals(responsableId))
                .toList();
    }
}
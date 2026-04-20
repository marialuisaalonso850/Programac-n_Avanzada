/**package co.edu.uniquindio.proyecto.infraestructure.repository;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.EstadoSolicitud;
import org.springframework.stereotype.Repository;

import java.util.*;


public class SolicitudRepositoryEnMemoria implements SolicitudRepository {

    private final Map<String, Solicitud> baseDatos = new HashMap<>();

    @Override
    public Solicitud save(Solicitud solicitud) {
        baseDatos.put(solicitud.getId().getValue().toString(), solicitud);
        return solicitud;
    }

    @Override
    public Optional<Solicitud> findById(String id) {
        return Optional.ofNullable(baseDatos.get(id));
    }

    @Override
    public List<Solicitud> findAll() {
        return new ArrayList<>(baseDatos.values());
    }

    @Override
    public List<Solicitud> findByEstado(EstadoSolicitud estado) {
        return baseDatos.values()
                .stream()
                .filter(s -> s.getEstado() == estado)
                .toList();
    }

    @Override
    public List<Solicitud> findBySolicitanteIdentificacion(DocumentoIdentidad usuarioId) {
        return baseDatos.values()
                .stream()
                .filter(s -> s.getUsuario().getIdentificacion().equals(usuarioId))
                .toList();
    }

    @Override
    public List<Solicitud> findByResponsableIdentificacion(DocumentoIdentidad responsableId) {
        return baseDatos.values()
                .stream()
                .filter(s -> s.getResponsable() != null &&
                        s.getResponsable().getIdentificacion().equals(responsableId))
                .toList();
    }
}**/
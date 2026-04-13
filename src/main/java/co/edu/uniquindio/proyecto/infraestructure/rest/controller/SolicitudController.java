package co.edu.uniquindio.proyecto.infraestructure.rest.controller;
import co.edu.uniquindio.proyecto.application.usecase.solicitudCase.*;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.asignarresponsable.AsignarResponsableRequest;
import co.edu.uniquindio.proyecto.application.dto.request.solicitud.crearsolicitud.CrearSolicitudRequest;
import co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud.ClasificarSolicitudRequest;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.historial.EventoHistorialResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.PaginaSolicitudes;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.SolicitudDetalleResponse;
import co.edu.uniquindio.proyecto.application.usecase.solicitudCase.*;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final CrearSolicitudUseCase crearSolicitudUseCase;
    private final ConsultarSolicitudesPorEstadoUseCase consultarUseCase;
    private final ClasificarSolicitudUseCase clasificarUseCase;
    private final AsignarResponsableUseCase asignarUseCase;
    private final SolicitudMapper mapper;

    // RF-01: Registro de solicitudes
    @PostMapping
    public ResponseEntity<SolicitudDetalleResponse> crear(@Valid @RequestBody CrearSolicitudRequest request) {
        Solicitud solicitud = crearSolicitudUseCase.ejecutar(request);
        SolicitudDetalleResponse response = mapper.toDetalleResponse(solicitud);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest().path("/solicitudes/{id}")
                .buildAndExpand(solicitud.getId().getValue()).toUri();

        return ResponseEntity.created(location).body(response);
    }

    // RF-07: Consulta y Seguimiento (Listado Paginado)
    @GetMapping
    public ResponseEntity<PaginaSolicitudes> listar(
            @RequestParam(required = false) EstadoSolicitud estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PaginaSolicitudes respuesta = consultarUseCase.ejecutarPaginado(estado, page, size);
        return ResponseEntity.ok(respuesta);
    }

    // Obtener detalle de una solicitud específica
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDetalleResponse> obtenerPorId(@PathVariable String id) {
        Solicitud solicitud = consultarUseCase.obtenerPorId(id);
        SolicitudDetalleResponse response = mapper.toDetalleResponse(solicitud);
        return ResponseEntity.ok(response);
    }

    // RF-02: Clasificación y Priorización
    @PutMapping("/{id}/clasificar")
    public ResponseEntity<SolicitudDetalleResponse> clasificar(
            @PathVariable String id,
            @Valid @RequestBody ClasificarSolicitudRequest request) {
        Solicitud actualizada = clasificarUseCase.ejecutar(id, request);
        return ResponseEntity.ok(mapper.toDetalleResponse(actualizada));
    }

    // RF-05: Asignación de Responsables
    @PutMapping("/{id}/asignar")
    public ResponseEntity<SolicitudDetalleResponse> asignar(
            @PathVariable String id,
            @Valid @RequestBody AsignarResponsableRequest request) {

        SolicitudId solicitudId = new SolicitudId(UUID.fromString(id));

        DocumentoIdentidad respId = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, request.responsableId());

        DocumentoIdentidad gestorId = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, request.responsableId());

        Solicitud actualizada = asignarUseCase.ejecutar(solicitudId, respId, gestorId);

        return ResponseEntity.ok(mapper.toDetalleResponse(actualizada));
    }

    // RF-06: Trazabilidad (Historial)
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<EventoHistorialResponse>> obtenerHistorial(@PathVariable String id) {
        List<Historial> historial = consultarUseCase.obtenerHistorial(id);
        List<EventoHistorialResponse> response = mapper.toHistorialResponseList(historial);
        return ResponseEntity.ok(response);
    }
}
package co.edu.uniquindio.proyecto.infraestructure.rest.controller;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.AtenderSolicitud.AtenderSolicitudRequest;
import co.edu.uniquindio.proyecto.application.dto.request.solicitud.CancelarSolicitud.CancelarSolicitudRequest;
import co.edu.uniquindio.proyecto.application.dto.request.solicitud.asignarresponsable.AsignarResponsableRequest;
import co.edu.uniquindio.proyecto.application.dto.request.solicitud.cerrarSolicitud.CerrarSolicitudRequest;
import co.edu.uniquindio.proyecto.application.dto.request.solicitud.clasificarsolicitud.ClasificarSolicitudRequest;
import co.edu.uniquindio.proyecto.application.dto.request.solicitud.crearsolicitud.CrearSolicitudRequest;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.historial.EventoHistorialResponse;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.listasolicitud.PaginaSolicitudes;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.SolicitudDetalleResponse;
import co.edu.uniquindio.proyecto.application.usecase.solicitudCase.*;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapper;
import co.edu.uniquindio.proyecto.infraestructure.rest.security.helper.UsuarioAutenticado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@Tag(name = "Solicitudes", description = "Gestión del agregado Solicitud")
public class SolicitudController {

    private final CrearSolicitudUseCase crearSolicitudUseCase;
    private final ConsultarSolicitudesPorEstadoUseCase consultarUseCase;
    private final ClasificarSolicitudUseCase clasificarUseCase;
    private final AsignarResponsableUseCase asignarUseCase;
    private final MarcarAtendidaUseCase marcarAtendidaUseCase;
    private final CerrarSolicitudUseCase cerrarUseCase;
    private final CancelarSolicitudUseCase cancelarUseCase;
    private final UsuarioAutenticado usuarioAutenticado;
    private final SolicitudMapper mapper;

    @PostMapping
    @Operation(summary = "Crear una nueva solicitud")
    public ResponseEntity<SolicitudDetalleResponse> crearSolicitud(
            @Valid @RequestBody CrearSolicitudRequest request) {

        // Se obtiene el documento directamente del Token JWT
        DocumentoIdentidad solicitanteDoc = usuarioAutenticado.getDocumentoIdentidad();
        Solicitud solicitud = crearSolicitudUseCase.ejecutar(request, solicitanteDoc);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(solicitud.getId().getValue()).toUri();

        return ResponseEntity.created(location).body(mapper.toDetalleResponse(solicitud));
    }

    @PutMapping("/{id}/atender")
    @Operation(summary = "Marcar solicitud como atendida")
    public ResponseEntity<SolicitudDetalleResponse> atender(
            @PathVariable String id,
            @Valid @RequestBody AtenderSolicitudRequest request) {

        Solicitud solicitud = marcarAtendidaUseCase.ejecutar(id, request.observacion());
        return ResponseEntity.ok(mapper.toDetalleResponse(solicitud));
    }

    @PutMapping("/{id}/cerrar")
    @Operation(summary = "Cerrar definitivamente una solicitud")
    public ResponseEntity<SolicitudDetalleResponse> cerrar(
            @PathVariable String id,
            @Valid @RequestBody CerrarSolicitudRequest request) {

        Solicitud solicitud = cerrarUseCase.ejecutar(id, request.observacion());
        return ResponseEntity.ok(mapper.toDetalleResponse(solicitud));
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar una solicitud")
    public ResponseEntity<SolicitudDetalleResponse> cancelar(
            @PathVariable String id,
            @Valid @RequestBody CancelarSolicitudRequest request) {

        // Extraemos quién cancela desde el Token (Seguridad mejorada)
        DocumentoIdentidad actorId = usuarioAutenticado.getDocumentoIdentidad();

        Solicitud solicitud = cancelarUseCase.ejecutar(id, actorId, request.motivo());
        return ResponseEntity.ok(mapper.toDetalleResponse(solicitud));
    }

    @GetMapping
    @Operation(summary = "Listar solicitudes con filtros y paginación")
    public ResponseEntity<PaginaSolicitudes> listarSolicitudes(
            @RequestParam(required = false) EstadoSolicitud estado,
            @RequestParam(required = false) Prioridad prioridad,
            @RequestParam(required = false) String solicitanteId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginaSolicitudes respuesta = consultarUseCase.ejecutarPaginado(estado, prioridad, solicitanteId, page, size);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de una solicitud por ID")
    public ResponseEntity<SolicitudDetalleResponse> obtenerSolicitudPorId(@PathVariable String id) {
        return ResponseEntity.ok(mapper.toDetalleResponse(consultarUseCase.obtenerPorId(id)));
    }

    @PutMapping("/{id}/clasificar")
    @Operation(summary = "Clasificar tipo y prioridad de solicitud")
    public ResponseEntity<SolicitudDetalleResponse> clasificarSolicitud(
            @PathVariable String id,
            @Valid @RequestBody ClasificarSolicitudRequest request) {
        Solicitud solicitud = clasificarUseCase.ejecutar(id, request);

        return ResponseEntity.ok(mapper.toDetalleResponse(solicitud));
    }

    @PutMapping("/{id}/asignar")
    @Operation(summary = "Asignar un responsable a la solicitud")
    public ResponseEntity<Void> asignarResponsable(
            @PathVariable String id,
            @Valid @RequestBody AsignarResponsableRequest request) {

        asignarUseCase.ejecutar(id, request.responsableId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/historial")
    @Operation(summary = "Consultar historial de eventos de una solicitud")
    public ResponseEntity<List<EventoHistorialResponse>> obtenerHistorial(@PathVariable String id) {
        var historial = consultarUseCase.obtenerHistorial(id);
        return ResponseEntity.ok(mapper.toHistorialResponseList(historial));
    }
}
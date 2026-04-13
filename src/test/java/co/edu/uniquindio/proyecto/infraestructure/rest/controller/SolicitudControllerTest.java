package co.edu.uniquindio.proyecto.infraestructure.rest.controller;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.asignarresponsable.AsignarResponsableRequest;
import co.edu.uniquindio.proyecto.application.dto.request.solicitud.crearsolicitud.CrearSolicitudRequest;
import co.edu.uniquindio.proyecto.application.dto.response.solicitud.solicitudcompleta.SolicitudDetalleResponse;
import co.edu.uniquindio.proyecto.application.usecase.solicitudCase.*;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SolicitudController.class)
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CrearSolicitudUseCase crearSolicitudUseCase;

    @MockitoBean
    private ConsultarSolicitudesPorEstadoUseCase consultarUseCase;

    @MockitoBean
    private ClasificarSolicitudUseCase clasificarUseCase;

    @MockitoBean
    private AsignarResponsableUseCase asignarUseCase;

    @MockitoBean
    private SolicitudMapper mapper;

    @Test
    void debeAsignarResponsableExitosamente() throws Exception {
        // 1. GIVEN (Preparación)
        String solicitudId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        AsignarResponsableRequest request = new AsignarResponsableRequest("123456789");

        // Simulamos que el use case devuelve una solicitud (mock)
        Solicitud solicitudMock = org.mockito.Mockito.mock(Solicitud.class);
        // Usamos any() para que Mockito acepte cualquier instancia de tus Value Objects
        when(asignarUseCase.ejecutar(any(SolicitudId.class), any(DocumentoIdentidad.class), any(DocumentoIdentidad.class)))
                .thenReturn(solicitudMock);

        // 2. WHEN (Acción) & 3. THEN (Verificación)
        mockMvc.perform(put("/api/solicitudes/{id}/asignar", solicitudId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void debeCrearSolicitudExitosamente() throws Exception {

        String uuidString = UUID.randomUUID().toString();
        SolicitudId idDominio = new SolicitudId(UUID.fromString(uuidString));

        CrearSolicitudRequest request = new CrearSolicitudRequest(

                "Problema con mi matricula de este semestre",
                CanalOrigen.CSU,
                "CEDULA_CIUDADANIA",
                "1094123456"
        );

        // 1. Configuramos el Mock de la Entidad para que NO devuelva null en el ID
        Solicitud solicitudMock = org.mockito.Mockito.mock(Solicitud.class);
        when(solicitudMock.getId()).thenReturn(idDominio);

        SolicitudDetalleResponse responseMock = new SolicitudDetalleResponse(
                uuidString, "SOL-001", null, request.descripcion(),
                request.canalOrigen(), EstadoSolicitud.REGISTRADA,
                null, null, null, null, null, 0
        );

        when(crearSolicitudUseCase.ejecutar(any(CrearSolicitudRequest.class))).thenReturn(solicitudMock);
        when(mapper.toDetalleResponse(solicitudMock)).thenReturn(responseMock);

        // WHEN & THEN
        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(uuidString))
                .andExpect(jsonPath("$.estado").value("REGISTRADA"));
    }
}
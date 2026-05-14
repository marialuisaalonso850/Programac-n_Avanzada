package co.edu.uniquindio.proyecto.infraestructure.rest.controller;

import co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario.CrearUsuarioRequest;
import co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario.DetalleUsuarioResponse;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.*;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.UsuarioMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CrearUsuarioUseCase crearUsuarioUseCase;

    @MockitoBean
    private ObtenerUsuarioUseCase obtenerUsuarioUseCase;

    @MockitoBean
    private ConsultarUsuariosUseCase consultarUsuariosUseCase;

    @MockitoBean
    private ObtenerUsuarioByEmailUseCase obtenerUsuarioByEmailUseCase;

    @MockitoBean
    private DesactivarUsuarioUseCase desactivarUsuarioUseCase;

    @MockitoBean
    private UsuarioMapper mapper;

    @Test
    void debeCrearUsuarioExitosamente() throws Exception {

        CrearUsuarioRequest request = new CrearUsuarioRequest(
                "1094123456", "CEDULA_CIUDADANIA", "Juan Perez", "juan@email.com", "ESTUDIANTE"
        );

        Usuario usuarioMock = mock(Usuario.class);
        DocumentoIdentidad docMock = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1094123456");

        when(mapper.toDomain(any(CrearUsuarioRequest.class))).thenReturn(usuarioMock);
        when(usuarioMock.getIdentificacion()).thenReturn(docMock);
        when(crearUsuarioUseCase.ejecutar(any(), any(), any(), any()))
                .thenReturn(usuarioMock);

        DetalleUsuarioResponse responseMock = new DetalleUsuarioResponse(
                "1094123456", "Juan Perez", "juan@email.com", "ESTUDIANTE", true
        );
        when(mapper.toDetalleResponse(any(Usuario.class))).thenReturn(responseMock);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Debería dar 201 ahora
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }
    @Test
    void debeObtenerUsuarioPorId() throws Exception {

        String id = "1094123456";
        Usuario usuarioMock = mock(Usuario.class);
        DetalleUsuarioResponse responseMock = new DetalleUsuarioResponse(
                id, "Juan Perez", "juan@email.com", "ESTUDIANTE", true
        );

        when(obtenerUsuarioUseCase.ejecutar(anyString(), anyString())).thenReturn(usuarioMock);
        when(mapper.toDetalleResponse(usuarioMock)).thenReturn(responseMock);

        mockMvc.perform(get("/api/usuarios/{id}", id)
                        .param("tipoDocumento", "CEDULA_CIUDADANIA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identificacion").value(id));
    }

    @Test
    void debeListarUsuarios() throws Exception {

        Usuario usuarioMock = mock(Usuario.class);
        DetalleUsuarioResponse responseMock = new DetalleUsuarioResponse(
                "1", "Test", "test@test.com", "ESTUDIANTE", true
        );

        when(consultarUsuariosUseCase.ejecutar()).thenReturn(List.of(usuarioMock));
        when(mapper.toDetalleResponse(any())).thenReturn(responseMock);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void debeObtenerPorEmail() throws Exception {

        String email = "juan@email.com";
        Usuario usuarioMock = mock(Usuario.class);
        DetalleUsuarioResponse responseMock = new DetalleUsuarioResponse(
                "1094123456", "Juan", email, "ESTUDIANTE", true
        );

        when(obtenerUsuarioByEmailUseCase.ejecutar(email)).thenReturn(usuarioMock);
        when(mapper.toDetalleResponse(usuarioMock)).thenReturn(responseMock);

        mockMvc.perform(get("/api/usuarios/por-email")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void debeDesactivarUsuario() throws Exception {

        mockMvc.perform(patch("/api/usuarios/{id}/desactivar", "1094")
                        .param("tipoDocumento", "CEDULA_CIUDADANIA")
                        .param("tieneSolicitudesActivas", "false"))
                .andExpect(status().isNoContent());

        verify(desactivarUsuarioUseCase).ejecutar(anyString(), anyString(), anyBoolean());
    }
}

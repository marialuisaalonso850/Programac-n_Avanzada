package co.edu.uniquindio.proyecto.infraestructure.rest.controller;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.crearsolicitud.CrearSolicitudRequest;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
public class SolicitudControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private WebApplicationContext context;


    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Configuramos MockMvc manualmente usando el contexto de la aplicación
        // e incluyendo la configuración de seguridad de Spring.
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        this.objectMapper = new ObjectMapper();

        this.objectMapper.findAndRegisterModules();

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }

    @Test
    @WithMockUser(username = "1001", roles = {"ESTUDIANTE"})
    @DisplayName("Debe crear una solicitud exitosamente")
    void crearSolicitudTest() throws Exception {

        // 1. CREAR EL OBJETO USUARIO (Sintaxis Builder)
        Usuario estudiante = Usuario.builder()
                .identificacion(new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1001"))
                .nombre("Estudiante de Prueba")
                .email(new Email("test@uniquindio.edu.co"))
                .tipoUsuario(TipoUsuario.ESTUDIANTE)
                .estado(EstadoUsuario.ACTIVO)
                .build();

        usuarioRepository.crearUsuario(estudiante, "password123");

        // 3. PREPARAR EL REQUEST
        CrearSolicitudRequest request = new CrearSolicitudRequest(
                TipoSolicitud.REGISTRO_ASIGNATURAS,
                "Descripción detallada de prueba con más de veinte caracteres",
                CanalOrigen.CSU
        );

        // 4. EJECUTAR
        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(result -> System.out.println("RESPUESTA DEL SERVIDOR: " + result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Debe listar las solicitudes")
    void listarSolicitudesTest() throws Exception {
        mockMvc.perform(get("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
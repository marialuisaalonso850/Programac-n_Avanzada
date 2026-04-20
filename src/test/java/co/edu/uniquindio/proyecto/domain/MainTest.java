package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.application.dto.request.solicitud.crearsolicitud.CrearSolicitudRequest;
import co.edu.uniquindio.proyecto.application.usecase.solicitudCase.*;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.CrearUsuarioUseCase;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.SolicitudService.AsignarResponsableService;
import co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuariobyid.ObtenerUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapper;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapperImpl;
import co.edu.uniquindio.proyecto.infraestructure.rest.security.helper.UsuarioAutenticado;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class MainTest {

    public static void main(String[] args) {

        // 1. REPOSITORIOS (Deben estar instanciados con tu implementación real de JPA/DB)
        SolicitudRepository solicitudRepository = null;
        UsuarioRepository usuarioRepository = null;
        SolicitudMapper mapper = new SolicitudMapperImpl();

        // 2. MOCKS MANUALES (Necesarios para que los constructores funcionen fuera de Spring)
        PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override public String encode(CharSequence raw) { return "hash_" + raw; }
            @Override public boolean matches(CharSequence raw, String encoded) { return true; }
        };

        UsuarioAutenticado authHelper = new UsuarioAutenticado() {
            @Override public DocumentoIdentidad getDocumentoIdentidad() {
                return new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "24603863");
            }
        };

        // 3. SERVICIOS
        ObtenerUsuarioService obtenerUsuarioService = new ObtenerUsuarioService(usuarioRepository);
        AsignarResponsableService asignarResponsableService = new AsignarResponsableService();

        // 4. INSTANCIACIÓN DE USE CASES (Aquí es donde pasas solo los argumentos que pide el constructor)

        // Constructor pide: (UsuarioRepository, PasswordEncoder) -> 2 argumentos
        CrearUsuarioUseCase crearUsuario = new CrearUsuarioUseCase(usuarioRepository, passwordEncoder);

        // Constructor pide: (SolicitudRepository, ObtenerUsuarioService) -> 2 argumentos
        CrearSolicitudUseCase crearSolicitud = new CrearSolicitudUseCase(solicitudRepository, obtenerUsuarioService);

        // Constructor pide: (SolicitudRepository, UsuarioRepository, AsignarResponsableService, UsuarioAutenticado) -> 4 argumentos
        AsignarResponsableUseCase asignar = new AsignarResponsableUseCase(
                solicitudRepository,
                usuarioRepository,
                asignarResponsableService,
                authHelper
        );

        CerrarSolicitudUseCase cerrar = new CerrarSolicitudUseCase(solicitudRepository);

        // --- 5. INICIO DEL FLUJO DE PRUEBA (Aquí usas los métodos .ejecutar()) ---

        DocumentoIdentidad responsableId = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "24603863");
        DocumentoIdentidad clienteId = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1007730781");

        System.out.println("--- Ejecutando registros ---");

        // AQUÍ es donde el método recibe los 5 parámetros, NO en el constructor
        crearUsuario.ejecutar(responsableId, "Andres Docente", new Email("andres@uniquindio.com"), TipoUsuario.DOCENTE, "admin123");
        crearUsuario.ejecutar(clienteId, "John Estudiante", new Email("john@gmail.com"), TipoUsuario.ESTUDIANTE, "john123");

        // Registro de Solicitud
        SolicitudId solId1 = SolicitudId.generar();

// 2. Crear el DTO (El Request)
        CrearSolicitudRequest request = new CrearSolicitudRequest(
                TipoSolicitud.REGISTRO_ASIGNATURAS,
                "Problema con reingreso",
                CanalOrigen.CSU
        );

// 3. Ejecutar con los 3 argumentos exactos
        crearSolicitud.ejecutar( request, clienteId);

        Solicitud s1 = solicitudRepository.findById(solId1.getValue().toString())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Usuario admin = usuarioRepository.obtenerPorIdentificacion(responsableId).get();

        // Clasificar y guardar
        s1.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, Prioridad.ALTA, admin, "Prioridad alta por tiempo");
        solicitudRepository.save(s1);

        // Asignar responsable
        asignar.ejecutar(solId1.getValue().toString(), responsableId.numero());

        System.out.println("Flujo terminado correctamente.");
    }
}
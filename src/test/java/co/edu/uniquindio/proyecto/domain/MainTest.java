package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.application.usecase.*;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.CrearUsuarioUseCase; // Tu nuevo caso de uso // Ajusta el package según tu estructura
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.repository.SolicitudRepositoryEnMemoria;
import co.edu.uniquindio.proyecto.infraestructure.repository.UsuarioRepositoryEnMemoria;

import java.util.List;

public class MainTest {

    public static void main(String[] args) {

        // 1. Inicialización de Repositorios (Infraestructura)
        SolicitudRepository solicitudRepository = new SolicitudRepositoryEnMemoria();
        UsuarioRepository usuarioRepository = new UsuarioRepositoryEnMemoria();

        // 2. Inicialización de Casos de Uso (Aplicación)
        // Inyectamos los repositorios en los constructores
        CrearUsuarioUseCase crearUsuario = new CrearUsuarioUseCase(usuarioRepository);

        CrearSolicitudUseCase crearSolicitud =
                new CrearSolicitudUseCase(solicitudRepository, usuarioRepository);

        AsignarResponsableUseCase asignar =
                new AsignarResponsableUseCase(solicitudRepository, usuarioRepository);


        CerrarSolicitudUseCase cerrar =
                new CerrarSolicitudUseCase(solicitudRepository);

        ConsultarSolicitudesPorEstadoUseCase consultar =
                new ConsultarSolicitudesPorEstadoUseCase(solicitudRepository);

        // --- PRUEBA DE FLUJO ---

        // 3. Crear Usuarios usando el UseCase (o directamente si es para preparar la prueba)
        DocumentoIdentidad responsableId = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "24603863");
        Email emailResp = new Email("andres@uniquindio.com");

        crearUsuario.ejecutar(responsableId, "Andres", emailResp, TipoUsuario.DOCENTE);

        DocumentoIdentidad clienteId = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1007730781");
        Email emailCliente = new Email("john@gmail.com");

        crearUsuario.ejecutar(clienteId, "John", emailCliente, TipoUsuario.DOCENTE);

        // 2. Recuperarlo del repositorio para mostrar el estado final
        Usuario usuarioGuardado = usuarioRepository.obtenerPorIdentificacion(clienteId)
                .orElseThrow(() -> new RuntimeException("Error: El usuario no se guardó"));

// 3. Imprimir el cuerpo     completo
        System.out.println("Cuerpo del usuario registrado: " + usuarioGuardado);

        // 4. Crear una Solicitud
        SolicitudId solId = SolicitudId.generar();
        Descripcion desc = new Descripcion("La aplicación no guarda datos");

        crearSolicitud.ejecutar(solId, desc, TipoSolicitud.HOMOLOGACION, clienteId,
                Prioridad.ALTA, CanalOrigen.CSU);

        System.out.println("Solicitud creada correctamente con ID: " + solId.toString());

        // 5. Flujo de estados
        // Necesitamos recuperar al objeto responsable para algunas acciones de dominio
        Usuario responsable = usuarioRepository.obtenerPorIdentificacion(responsableId)
                .orElseThrow();


        asignar.ejecutar(solId, responsableId);


        // 6. Consultar
        List<Solicitud> atendidas = consultar.ejecutar(EstadoSolicitud.ATENDIDA);
        System.out.println("Solicitudes atendidas encontradas: " + atendidas.size());

        // 7. Cerrar
        cerrar.ejecutar(solId, "Problema solucionado definitivamente");
        System.out.println("Solicitud finalizada con éxito.");
    }
}
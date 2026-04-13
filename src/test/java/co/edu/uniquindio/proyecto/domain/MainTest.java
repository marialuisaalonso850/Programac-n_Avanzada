package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.application.usecase.solicitudCase.*;
import co.edu.uniquindio.proyecto.application.usecase.usuariocase.CrearUsuarioUseCase;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.repository.solicitud.SolicitudRepository;
import co.edu.uniquindio.proyecto.domain.repository.usuario.UsuarioRepository;
import co.edu.uniquindio.proyecto.domain.service.usuario.crearusuario.CrearUsuarioService;
import co.edu.uniquindio.proyecto.domain.service.usuario.obtenerusuariobyid.ObtenerUsuarioService;
import co.edu.uniquindio.proyecto.domain.valueobject.*;
import co.edu.uniquindio.proyecto.infraestructure.repository.SolicitudRepositoryEnMemoria;
import co.edu.uniquindio.proyecto.infraestructure.repository.UsuarioRepositoryEnMemoria;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapper;
import co.edu.uniquindio.proyecto.infraestructure.rest.mapper.SolicitudMapperImpl;

public class MainTest {

    public static void main(String[] args) {

        SolicitudRepository solicitudRepository = new SolicitudRepositoryEnMemoria();
        UsuarioRepository usuarioRepository = new UsuarioRepositoryEnMemoria();
        SolicitudMapper mapper = new SolicitudMapperImpl();

        ObtenerUsuarioService obtenerUsuarioService = new ObtenerUsuarioService(usuarioRepository);
        CrearUsuarioService crearUsuarioService = new CrearUsuarioService(usuarioRepository);

        CrearUsuarioUseCase crearUsuario = new CrearUsuarioUseCase(crearUsuarioService);
        CrearSolicitudUseCase crearSolicitud = new CrearSolicitudUseCase(solicitudRepository, obtenerUsuarioService);
        AsignarResponsableUseCase asignar = new AsignarResponsableUseCase(solicitudRepository, obtenerUsuarioService);
        CerrarSolicitudUseCase cerrar = new CerrarSolicitudUseCase(solicitudRepository);
        ConsultarSolicitudesPorEstadoUseCase consultar = new ConsultarSolicitudesPorEstadoUseCase(solicitudRepository, mapper);

        DocumentoIdentidad responsableId = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "24603863");
        crearUsuario.ejecutar(responsableId, "Andres Docente", new Email("andres@uniquindio.com"), TipoUsuario.DOCENTE);

        DocumentoIdentidad clienteId = new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1007730781");
        crearUsuario.ejecutar(clienteId, "John Estudiante", new Email("john@gmail.com"), TipoUsuario.ESTUDIANTE);

        Usuario john = usuarioRepository.obtenerPorIdentificacion(clienteId)
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado"));

        System.out.println("--- Iniciando Flujo de Solicitud Exitosa ---");

        SolicitudId solId1 = SolicitudId.generar();
        crearSolicitud.ejecutar(solId1, new Descripcion("Problema con reingreso"), clienteId, Prioridad.MEDIA, CanalOrigen.CSU);

        Solicitud s1 = solicitudRepository.findById(solId1.getValue())
                .orElseThrow(() -> new RuntimeException("No se encontró la solicitud 1"));

        s1.clasificar(TipoSolicitud.REGISTRO_ASIGNATURAS, john, "Se clasifica correctamente");
        solicitudRepository.save(s1);

        asignar.ejecutar(solId1, responsableId, clienteId);

        s1.marcarComoAtendida("El estudiante ya puede matricular");
        solicitudRepository.save(s1);

        cerrar.ejecutar(solId1, "Proceso finalizado exitosamente");


        s1 = solicitudRepository.findById(solId1.getValue()).get();
        System.out.println("Solicitud 1 finalizada. Estado actual: " + s1.getEstado());

        System.out.println("\n--- Iniciando Flujo de Cancelación ---");

        SolicitudId solId2 = SolicitudId.generar();
        crearSolicitud.ejecutar(solId2, new Descripcion("Solicitud para cancelar"), clienteId, Prioridad.BAJA, CanalOrigen.CSU);

        Solicitud s2 = solicitudRepository.findById(solId2.getValue())
                .orElseThrow(() -> new RuntimeException("No se encontró la solicitud 2"));

        s2.cancelar(john, "El estudiante desistió del proceso");
        solicitudRepository.save(s2);

        System.out.println("Solicitud 2 finalizada. Estado actual: " + s2.getEstado());
    }
}
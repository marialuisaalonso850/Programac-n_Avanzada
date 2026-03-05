package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.application.usecase.*;
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

        SolicitudRepository solicitudRepository = new SolicitudRepositoryEnMemoria();
        UsuarioRepository usuarioRepository = new UsuarioRepositoryEnMemoria();

        CrearSolicitudUseCase crear =
                new CrearSolicitudUseCase(solicitudRepository, usuarioRepository);

        AsignarResponsableUseCase asignar =
                new AsignarResponsableUseCase(solicitudRepository, usuarioRepository);

        CambiarEstadoUseCase cambiarEstado =
                new CambiarEstadoUseCase(solicitudRepository);

        CerrarSolicitudUseCase cerrar =
                new CerrarSolicitudUseCase(solicitudRepository);

        ConsultarSolicitudesPorEstadoUseCase consultar =
                new ConsultarSolicitudesPorEstadoUseCase(solicitudRepository);

        SolicitudId id = SolicitudId.generar();

        Descripcion descripcion =
                new Descripcion("La aplicación no guarda datos");

        DocumentoIdentidad documento =
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "1007730781");

        DocumentoIdentidad responsableId =
                new DocumentoIdentidad(TipoDocumento.CEDULA_CIUDADANIA, "24603863");

        Usuario responsable = new Usuario(responsableId, "Andres",
                new Email("andres@uniquindio.com"), TipoUsuario.DOCENTE);

        Usuario usuario = new Usuario(documento, "John",
                new Email("john@gmail.com"), TipoUsuario.DOCENTE);

        usuarioRepository.guardar(responsable);
        usuarioRepository.guardar(usuario);

        crear.ejecutar(id, descripcion, TipoSolicitud.HOMOLOGACION, documento, Prioridad.ALTA,
                CanalOrigen.CSU);

        System.out.println("Solicitud creada correctamente");

        cambiarEstado.ejecutar(
                id,
                EstadoSolicitud.CLASIFICADA,
                "Clasificación inicial",
                responsable
        );

        asignar.ejecutar(id, responsableId);

        cambiarEstado.ejecutar(
                id,
                EstadoSolicitud.ATENDIDA,
                "Solicitud solucionada",
                responsable
        );

        List<Solicitud> enAtencion =
                consultar.ejecutar(EstadoSolicitud.ATENDIDA);

        System.out.println("Solicitudes en atención:");
        enAtencion.forEach(System.out::println);

        cerrar.ejecutar(id, "Problema solucionado");

        System.out.println("Solicitud cerrada correctamente");
    }
}
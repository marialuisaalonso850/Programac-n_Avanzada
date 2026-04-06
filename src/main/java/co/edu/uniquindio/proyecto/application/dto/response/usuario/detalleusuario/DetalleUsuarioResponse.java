package co.edu.uniquindio.proyecto.application.dto.response.usuario.detalleusuario;

public record DetalleUsuarioResponse(
        String identificacion,
        String nombre,
        String email,
        String tipoUsuario,
        boolean activo
) {}

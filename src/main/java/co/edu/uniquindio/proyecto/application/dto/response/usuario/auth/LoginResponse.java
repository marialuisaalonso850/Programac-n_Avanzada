package co.edu.uniquindio.proyecto.application.dto.response.usuario.auth;

public record LoginResponse(
        String token,
        String tipoUsuario,
        String nombre
) {}
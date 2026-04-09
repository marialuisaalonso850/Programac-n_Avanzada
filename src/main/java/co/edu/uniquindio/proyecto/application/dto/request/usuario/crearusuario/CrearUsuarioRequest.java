package co.edu.uniquindio.proyecto.application.dto.request.usuario.crearusuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearUsuarioRequest(
        @NotBlank(message = "La identificación es obligatoria")
        String identificacion,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100)
        String nombre,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser un formato de email válido")
        String email,

        @NotBlank(message = "El tipo de usuario es obligatorio")
        String tipoUsuario
) {}
package co.edu.uniquindio.proyecto.application.dto.request.usuario.auth;

import co.edu.uniquindio.proyecto.domain.valueobject.TipoDocumento;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        TipoDocumento tipoDocumento,

        @NotBlank String identificacion,

        @NotBlank String password
) {}

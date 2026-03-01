package co.edu.uniquindio.proyecto.domain.valueobject;

import co.edu.uniquindio.proyecto.domain.exception.ReglaDominioException;

public record DocumentoIdentidad(
        TipoDocumento tipo,
        String numero
) {

    public DocumentoIdentidad {

        if (tipo == null)
            throw new ReglaDominioException("El tipo de documento es obligatorio");

        if (numero == null || numero.isBlank())
            throw new ReglaDominioException("El número de documento es obligatorio");
    }
}
package co.edu.uniquindio.proyecto.domain.repositorio;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.valueobject.DocumentoIdentidad;

public interface UsuarioRepositorio {
    Usuario buscarUsuarioIdentificacion(DocumentoIdentidad identificacion);
}

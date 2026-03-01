package co.edu.uniquindio.proyecto.domain.exception;

public class PermisoDenegadoException extends RuntimeException {

    public PermisoDenegadoException(String mensaje) {
        super(mensaje);
    }

    public PermisoDenegadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
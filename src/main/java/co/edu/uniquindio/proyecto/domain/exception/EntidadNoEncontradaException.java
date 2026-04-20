package co.edu.uniquindio.proyecto.domain.exception;


public class EntidadNoEncontradaException extends RuntimeException {

    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
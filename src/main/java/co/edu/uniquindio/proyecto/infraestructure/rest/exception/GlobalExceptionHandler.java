package co.edu.uniquindio.proyecto.infraestructure.rest.exception;

import co.edu.uniquindio.proyecto.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ReglaDominioException.class, SolicitudCerradaException.class, EstadoInvalidoException.class})
    public ResponseEntity<Map<String, Object>> handleReglasDominio(RuntimeException ex) {
        return crearRespuestaError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PermisoDenegadoException.class)
    public ResponseEntity<Map<String, Object>> handlePermisoDenegado(PermisoDenegadoException ex) {
        return crearRespuestaError(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String mensaje = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return crearRespuestaError(HttpStatus.BAD_REQUEST, "Error de validación: " + mensaje);
    }


    private ResponseEntity<Map<String, Object>> crearRespuestaError(HttpStatus status, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("mensaje", mensaje);
        return new ResponseEntity<>(body, status);
    }
}

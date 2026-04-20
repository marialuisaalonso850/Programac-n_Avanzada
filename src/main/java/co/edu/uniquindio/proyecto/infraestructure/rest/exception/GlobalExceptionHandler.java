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

    // 422 - Regla de negocio violada
    @ExceptionHandler(ReglaDominioException.class)
    public ResponseEntity<Map<String, Object>> handleReglaDominio(ReglaDominioException ex) {
        return crearRespuestaError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    // 400 - Errores específicos de estado y cierre
    @ExceptionHandler({SolicitudCerradaException.class, EstadoInvalidoException.class})
    public ResponseEntity<Map<String, Object>> handleReglasEspecificas(RuntimeException ex) {
        return crearRespuestaError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 403 - Permiso denegado
    @ExceptionHandler(PermisoDenegadoException.class)
    public ResponseEntity<Map<String, Object>> handlePermisoDenegado(PermisoDenegadoException ex) {
        return crearRespuestaError(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    // 404 - Entidad no encontrada
    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleEntidadNoEncontrada(EntidadNoEncontradaException ex) {
        return crearRespuestaError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 404 - Usuario no encontrado
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        return crearRespuestaError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 409 - Usuario ya existe
    @ExceptionHandler(UsuarioYaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioYaExiste(UsuarioYaExisteException ex) {
        return crearRespuestaError(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 400 - Validaciones de @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return crearRespuestaError(HttpStatus.BAD_REQUEST, "Error de validación: " + mensaje);
    }

    // 500 - Error genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenerico(Exception ex) {
        return crearRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno: " + ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> crearRespuestaError(HttpStatus status, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("mensaje", mensaje);
        return new ResponseEntity<>(body, status);
    }

}

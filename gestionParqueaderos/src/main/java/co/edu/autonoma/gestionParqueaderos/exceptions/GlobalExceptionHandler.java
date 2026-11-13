package co.edu.autonoma.gestionParqueaderos.exception;

import co.edu.autonoma.gestionParqueaderos.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Manejador global de excepciones de la API REST.
 *
 * Centraliza la captura y el tratamiento de las excepciones generadas
 * durante el procesamiento de las solicitudes HTTP, convirtiéndolas
 * en respuestas estandarizadas mediante ApiErrorResponse.
 *
 * Gestiona errores de recursos no encontrados, reglas de negocio,
 * validaciones de datos, formatos incorrectos, restricciones de
 * integridad de la base de datos y errores internos del servidor,
 * asignando a cada caso el código HTTP correspondiente.
 *
 * Evita la duplicación de bloques try-catch en los controladores
 * y garantiza respuestas de error consistentes para los clientes
 * de la API.
 *
 * También registra los errores inesperados mediante Log4j2,
 * facilitando su identificación y diagnóstico.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja los errores cuando un recurso no existe.
     */

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException ex,
            HttpServletRequest request) {

        log.warn("Recurso no encontrado. Método: {}, Ruta: {}, Motivo: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return construirRespuesta(
                HttpStatus.NOT_FOUND,
                "RECURSO_NO_ENCONTRADO",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    /**
     * Maneja los incumplimientos de reglas del negocio.
     */
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ApiErrorResponse> manejarReglaNegocio(
            ReglaNegocioException ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.CONFLICT,
                "REGLA_NEGOCIO",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    /**
     * Maneja los errores de validación de los DTO mediante @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> manejarValidaciones(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> detalles = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatearErrorCampo)
                .toList();

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "ERROR_VALIDACION",
                "Uno o más campos contienen datos inválidos",
                request.getRequestURI(),
                detalles
        );
    }

    /**
     * Maneja errores de validación en parámetros de métodos.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> manejarRestricciones(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<String> detalles = ex.getConstraintViolations()
                .stream()
                .map(violacion -> violacion.getPropertyPath()
                        + ": " + violacion.getMessage())
                .toList();

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "ERROR_VALIDACION",
                "Los parámetros proporcionados no son válidos",
                request.getRequestURI(),
                detalles
        );
    }

    /**
     * Maneja cuerpos JSON incorrectos o incompatibles con los DTO.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> manejarJsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "JSON_INVALIDO",
                "El cuerpo de la solicitud contiene un formato inválido",
                request.getRequestURI(),
                List.of()
        );
    }

    /**
     * Maneja parámetros que no pueden convertirse al tipo esperado.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> manejarTipoParametro(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "TIPO_PARAMETRO_INVALIDO",
                "El parámetro '" + ex.getName()
                        + "' tiene un formato incorrecto",
                request.getRequestURI(),
                List.of()
        );
    }

    /**
     * Maneja conflictos con restricciones de integridad de la base de datos.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> manejarIntegridad(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.warn("Conflicto de integridad de datos. Método: {}, Ruta: {}",
                request.getMethod(),
                request.getRequestURI());

        return construirRespuesta(
                HttpStatus.CONFLICT,
                "CONFLICTO_INTEGRIDAD",
                "La operación incumple una restricción de integridad de los datos",
                request.getRequestURI(),
                List.of()
        );
    }

    /**
     * Maneja cualquier error no contemplado específicamente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> manejarErrorGeneral(
            Exception ex,
            HttpServletRequest request) {

        log.error("Error interno no controlado. Método: {}, Ruta: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex);

        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "ERROR_INTERNO",
                "Ocurrió un error interno en el servidor",
                request.getRequestURI(),
                List.of()
        );
    }

    /**
     * Construye una respuesta de error estandarizada.
     */
    private ResponseEntity<ApiErrorResponse> construirRespuesta(
            HttpStatus estado,
            String codigo,
            String mensaje,
            String ruta,
            List<String> detalles) {

        ApiErrorResponse respuesta = new ApiErrorResponse(
                LocalDateTime.now(),
                estado.value(),
                estado.getReasonPhrase(),
                codigo,
                mensaje,
                ruta,
                detalles
        );

        return ResponseEntity
                .status(estado)
                .body(respuesta);
    }

    /**
     * Formatea los errores individuales de validación.
     */
    private String formatearErrorCampo(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}

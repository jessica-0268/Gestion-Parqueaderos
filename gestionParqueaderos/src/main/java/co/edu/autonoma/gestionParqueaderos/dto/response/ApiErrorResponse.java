package co.edu.autonoma.gestionParqueaderos.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Estructura estandarizada para las respuestas de error de la API.
 */
public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String codigo,
        String mensaje,
        String ruta,
        List<String> detalles
) {}


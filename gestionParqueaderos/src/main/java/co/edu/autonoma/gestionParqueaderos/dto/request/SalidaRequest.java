package co.edu.autonoma.gestionParqueaderos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Datos necesarios para registrar la salida de un vehículo.
 * La fecha y hora de salida son asignadas por el servidor.
 */
public record SalidaRequest(

        @NotNull(message = "registradoSalidaPorId es obligatorio")
        @Positive(message = "registradoSalidaPorId debe ser un número positivo")
        Long registradoSalidaPorId

) {}
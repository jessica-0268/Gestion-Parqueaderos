package co.edu.autonoma.gestionParqueaderos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

/**
 * Datos necesarios para registrar el ingreso de un vehículo.
 * La fecha y hora de ingreso son asignadas por el servidor.
 */
public record IngresoRequest(

        @NotBlank(message = "La placa es obligatoria")
        @Pattern(
                regexp = "^[A-Za-z]{3}[0-9]{2}[0-9A-Za-z]$",
                message = "Formato de placa inválido (ej. ABC123 o ABC12D)"
        )
        String placa,

        @NotNull(message = "zonaId es obligatorio")
        @Positive(message = "zonaId debe ser un número positivo")
        Long zonaId,

        @NotNull(message = "registradoIngresoPorId es obligatorio")
        @Positive(message = "registradoIngresoPorId debe ser un número positivo")
        Long registradoIngresoPorId

) {}




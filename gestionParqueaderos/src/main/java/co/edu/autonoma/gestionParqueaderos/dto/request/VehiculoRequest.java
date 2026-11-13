package co.edu.autonoma.gestionParqueaderos.dto.request;

import co.edu.autonoma.gestionParqueaderos.model.enums.TipoVehiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record VehiculoRequest(

        @NotBlank(message = "La placa es obligatoria")
        @Pattern(
                regexp = "^[A-Za-z]{3}[0-9]{2}[0-9A-Za-z]$",
                message = "Formato de placa inválido (ej. ABC123 o ABC12D)"
        )
        String placa,

        @NotNull(message = "El tipo de vehículo es obligatorio")
        TipoVehiculo tipo,

        @Positive(message = "conductorId debe ser un número positivo")
        Long conductorId

) {}

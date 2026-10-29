package co.edu.autonoma.gestionParqueaderos.dto;

import java.time.LocalDateTime;

public record SalidaResponse(
        Long idTicket,
        String placa,
        LocalDateTime fechaIngreso,
        LocalDateTime fechaSalida,
        Double totalPagar
) {}
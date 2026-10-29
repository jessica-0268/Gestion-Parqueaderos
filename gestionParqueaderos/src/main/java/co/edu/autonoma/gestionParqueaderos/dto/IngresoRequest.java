package co.edu.autonoma.gestionParqueaderos.dto;

public record IngresoRequest(
        String placa,
        String tipoVehiculo
) {}
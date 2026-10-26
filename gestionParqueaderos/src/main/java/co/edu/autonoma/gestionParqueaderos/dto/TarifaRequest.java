package co.edu.autonoma.gestionParqueaderos.dto;

public record TarifaRequest(
        String tipoVehiculo,
        Double valorHora
) {}
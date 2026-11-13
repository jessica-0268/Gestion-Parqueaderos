package co.edu.autonoma.gestionParqueaderos.dto.response;

import co.edu.autonoma.gestionParqueaderos.model.enums.TipoVehiculo;

/**
 * Representa la información de un vehículo registrado,
 * incluyendo su placa, tipo y conductor asociado, si existe.
 */
public record VehiculoResponse(
        Long id,
        String placa,
        TipoVehiculo tipo,
        Long conductorId
) {

    public static VehiculoResponse desde(VehiculoEntity vehiculo) {
        Long conductorId = vehiculo.getConductor() == null
                ? null
                : vehiculo.getConductor().getId();

        return new VehiculoResponse(
                vehiculo.getId(),
                vehiculo.getPlaca(),
                vehiculo.getTipo(),
                conductorId
        );
    }
}


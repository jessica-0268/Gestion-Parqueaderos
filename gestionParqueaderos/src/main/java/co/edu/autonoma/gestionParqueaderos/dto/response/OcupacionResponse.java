package co.edu.autonoma.gestionParqueaderos.dto.response;

/**
 * Representa el estado de ocupación de una zona de parqueo.
 *
 * La cantidad de vehículos ocupados se obtiene contando
 * los ingresos activos asociados a la zona (RN-06).
 */
public record OcupacionResponse(
        Long zonaId,
        String zona,
        int capacidad,
        long ocupados,
        long disponibles,
        double porcentajeOcupacion
) {}


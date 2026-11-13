package co.edu.autonoma.gestionParqueaderos.dto.response;

import co.edu.autonoma.gestionParqueaderos.model.entity.ZonaEntity;

/**
 * Representa la información básica de una zona de parqueo,
 * incluyendo su identificador, nombre y capacidad máxima.
 */
public record ZonaResponse(
        Long id,
        String nombre,
        int capacidad
) {

    public static ZonaResponse desde(ZonaEntity zona) {
        return new ZonaResponse(
                zona.getId(),
                zona.getNombre(),
                zona.getCapacidad()
        );
    }
}

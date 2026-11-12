package co.edu.autonoma.gestionParqueaderos.dto.response;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa el resultado de la consulta de vigencia
 * de permisos de un vehículo mediante su placa.
 *
 * Se utiliza en GET /api/permisos/{placa}.
 * No expone información personal del conductor.
 */
public record VigenciaPermisoResponse(
        String placa,
        LocalDate fechaConsulta,
        boolean vigente,
        List<PermisoResponse> permisos
) {}


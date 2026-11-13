package co.edu.autonoma.gestionParqueaderos.dto.response;

import co.edu.autonoma.gestionParqueaderos.model.entity.PermisoEntity;
import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoPermiso;

import java.time.LocalDate;

/**
 * Representa la información de un permiso vehicular,
 * incluyendo su período de validez y estado de vigencia.
 *
 * Se utiliza para consultar los permisos asociados
 * a un vehículo y verificar su validez (RN-01).
 */
public record PermisoResponse(
        Long id,
        String placa,
        Long zonaId,
        String zona,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        EstadoPermiso estado,
        boolean vigente
) {

    public static PermisoResponse desde(PermisoEntity permiso, LocalDate hoy) {
        return new PermisoResponse(
                permiso.getId(),
                permiso.getVehiculo().getPlaca(),
                permiso.getZona().getId(),
                permiso.getZona().getNombre(),
                permiso.getFechaInicio(),
                permiso.getFechaFin(),
                permiso.getEstado(),
                permiso.estaVigenteEn(hoy)
        );
    }
}


package co.edu.autonoma.gestionParqueaderos.dto.response;

import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoIngreso;

import java.time.LocalDateTime;

/**
 * Representa la información de un ingreso vehicular,
 * incluyendo los datos del vehículo, la zona, el permiso
 * asociado y los registros de entrada y salida.
 *
 * Se utiliza para devolver información de ingresos
 * activos e históricos en las respuestas de la API.
 */

public record IngresoResponse(
        Long id,
        String placa,
        Long zonaId,
        String zona,
        Long permisoId,
        LocalDateTime fechaIngreso,
        LocalDateTime fechaSalida,
        EstadoIngreso estado,
        Long registradoIngresoPorId,
        Long registradoSalidaPorId
) {

    public static IngresoResponse desde(IngresoEntity ingreso) {
        return new IngresoResponse(
                ingreso.getId(),
                ingreso.getPermiso().getVehiculo().getPlaca(),
                ingreso.getPermiso().getZona().getId(),
                ingreso.getPermiso().getZona().getNombre(),
                ingreso.getPermiso().getId(),
                ingreso.getFechaIngreso(),
                ingreso.getFechaSalida(),
                ingreso.getEstado(),
                ingreso.getRegistradoIngresoPor().getId(),
                ingreso.getRegistradoSalidaPor() == null
                        ? null
                        : ingreso.getRegistradoSalidaPor().getId()
        );
    }
}


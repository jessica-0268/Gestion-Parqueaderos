package co.edu.autonoma.gestionParqueaderos.service;

import co.edu.autonoma.gestionParqueaderos.dto.request.ZonaRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.OcupacionResponse;
import co.edu.autonoma.gestionParqueaderos.dto.response.ZonaResponse;
import co.edu.autonoma.gestionParqueaderos.exception.RecursoNoEncontradoException;
import co.edu.autonoma.gestionParqueaderos.exception.ReglaNegocioException;
import co.edu.autonoma.gestionParqueaderos.model.entity.ZonaEntity;
import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoIngreso;
import co.edu.autonoma.gestionParqueaderos.repository.IngresoRepository;
import co.edu.autonoma.gestionParqueaderos.repository.ZonaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ZonaService {

    private final ZonaRepository zonaRepository;
    private final IngresoRepository ingresoRepository;

    public ZonaService(
            ZonaRepository zonaRepository,
            IngresoRepository ingresoRepository) {
        this.zonaRepository = zonaRepository;
        this.ingresoRepository = ingresoRepository;
    }

    /**
     * Registra una nueva zona de parqueo.
     *
     * @param request datos de la zona que se desea registrar.
     * @return información de la zona registrada.
     */
    public ZonaResponse registrar(ZonaRequest request) {

        log.info("Iniciando registro de zona. Nombre: {}, Capacidad: {}",
                request.nombre(), request.capacidad());

        ZonaEntity zona = new ZonaEntity(
                request.nombre(),
                request.capacidad()
        );

        ZonaEntity guardada = zonaRepository.save(zona);

        log.info("Zona registrada correctamente. ID: {}, Nombre: {}, Capacidad: {}",
                guardada.getId(),
                guardada.getNombre(),
                guardada.getCapacidad());

        return ZonaResponse.desde(guardada);
    }

    /**
     * Actualiza la configuración de una zona de parqueo.
     * No permite reducir la capacidad por debajo de su ocupación actual.
     */
    public ZonaResponse actualizar(Long id, ZonaRequest request) {

        log.info("Iniciando actualización de zona. ID: {}, Nuevo nombre: {}, Nueva capacidad: {}",
                id, request.nombre(), request.capacidad());

        ZonaEntity zona = zonaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una zona con el ID: " + id
                ));

        long ocupados = ingresoRepository.countByPermiso_Zona_IdAndEstado(
                id,
                EstadoIngreso.ACTIVO
        );

        if (request.capacidad() < ocupados) {

            log.warn("Actualización de zona rechazada. ID: {}, Capacidad solicitada: {}, Ocupación actual: {}",
                    id, request.capacidad(), ocupados);

            throw new ReglaNegocioException(
                    "La capacidad no puede ser inferior a la ocupación actual de la zona: "
                            + ocupados + " vehículos"
            );
        }

        zona.reemplazarConfiguracion(request.nombre(), request.capacidad());

        log.info("Zona actualizada correctamente. ID: {}, Nombre: {}, Capacidad: {}, Ocupados: {}",
                zona.getId(), zona.getNombre(), zona.getCapacidad(), ocupados);

        return ZonaResponse.desde(zona);
    }

    /**
     * Consulta la ocupación actual de una zona de parqueo.
     * La ocupación se calcula contando sus ingresos activos (RN-06).
     */
    @Transactional(readOnly = true)
    public OcupacionResponse consultarOcupacion(Long id) {

        log.info("Consultando ocupación de la zona. ID: {}", id);

        ZonaEntity zona = zonaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una zona con el ID: " + id
                ));

        long ocupados = ingresoRepository.countByPermiso_Zona_IdAndEstado(
                id,
                EstadoIngreso.ACTIVO
        );

        long disponibles = zona.getCapacidad() - ocupados;

        double porcentajeOcupacion = (ocupados * 100.0) / zona.getCapacidad();

        porcentajeOcupacion = Math.round(porcentajeOcupacion * 100.0) / 100.0;

        log.info("Ocupación consultada correctamente. Zona ID: {}, Nombre: {}, Ocupados: {}, Disponibles: {}, Porcentaje: {}%",
                zona.getId(),
                zona.getNombre(),
                ocupados,
                disponibles,
                porcentajeOcupacion);

        return new OcupacionResponse(
                zona.getId(),
                zona.getNombre(),
                zona.getCapacidad(),
                ocupados,
                disponibles,
                porcentajeOcupacion
        );
    }
}

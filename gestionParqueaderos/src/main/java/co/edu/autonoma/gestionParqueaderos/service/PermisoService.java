package co.edu.autonoma.gestionParqueaderos.service;

import co.edu.autonoma.gestionParqueaderos.dto.request.PermisoRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.PermisoResponse;
import co.edu.autonoma.gestionParqueaderos.dto.response.VigenciaPermisoResponse;
import co.edu.autonoma.gestionParqueaderos.exception.RecursoNoEncontradoException;
import co.edu.autonoma.gestionParqueaderos.exception.ReglaNegocioException;
import co.edu.autonoma.gestionParqueaderos.model.entity.PermisoEntity;
import co.edu.autonoma.gestionParqueaderos.model.entity.VehiculoEntity;
import co.edu.autonoma.gestionParqueaderos.model.entity.ZonaEntity;
import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoIngreso;
import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoPermiso;
import co.edu.autonoma.gestionParqueaderos.repository.IngresoRepository;
import co.edu.autonoma.gestionParqueaderos.repository.PermisoRepository;
import co.edu.autonoma.gestionParqueaderos.repository.VehiculoRepository;
import co.edu.autonoma.gestionParqueaderos.repository.ZonaRepository;
import co.edu.autonoma.gestionParqueaderos.util.PlacaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PermisoService {

    private final PermisoRepository permisoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ZonaRepository zonaRepository;
    private final IngresoRepository ingresoRepository;

    public PermisoService(PermisoRepository permisoRepository, VehiculoRepository vehiculoRepository, ZonaRepository zonaRepository, IngresoRepository ingresoRepository) {
        this.permisoRepository = permisoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.zonaRepository = zonaRepository;
        this.ingresoRepository = ingresoRepository;
    }

    /**
     * Registra un permiso para un vehículo en una zona.
     */
    public PermisoResponse registrar(PermisoRequest request) {

        String placa = PlacaUtil.normalizar(request.placa());

        log.info("Iniciando registro de permiso. Placa: {}, Zona ID: {}, Fecha inicio: {}, Fecha fin: {}",
                placa, request.zonaId(), request.fechaInicio(), request.fechaFin());

        if (request.fechaFin().isBefore(request.fechaInicio())) {

            log.warn("Registro de permiso rechazado: fecha fin {} anterior a fecha inicio {}. Placa: {}",
                    request.fechaFin(), request.fechaInicio(), placa);

            throw new ReglaNegocioException(
                    "La fecha de finalización no puede ser anterior a la fecha de inicio"
            );
        }

        VehiculoEntity vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un vehículo registrado con la placa: " + placa
                ));

        ZonaEntity zona = zonaRepository.findById(request.zonaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una zona con el ID: " + request.zonaId()
                ));

        PermisoEntity permiso = new PermisoEntity(
                vehiculo,
                zona,
                request.fechaInicio(),
                request.fechaFin()
        );

        PermisoEntity permisoGuardado = permisoRepository.save(permiso);

        log.info("Permiso registrado correctamente. ID: {}, Placa: {}, Zona ID: {}",
                permisoGuardado.getId(), placa, zona.getId());

        return PermisoResponse.desde(permisoGuardado, LocalDate.now());
    }

    /**
     * Consulta los permisos activos asociados a una placa
     * y determina si alguno está vigente en la fecha actual.
     */
    @Transactional(readOnly = true)
    public VigenciaPermisoResponse consultarPorPlaca(String placa) {

        String placaNormalizada = PlacaUtil.normalizar(placa);

        log.info("Consultando permisos del vehículo con placa: {}", placaNormalizada);

        vehiculoRepository.findByPlaca(placaNormalizada)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un vehículo registrado con la placa: "
                                + placaNormalizada
                ));

        LocalDate hoy = LocalDate.now();

        List<PermisoEntity> permisos = permisoRepository
                .findAllByVehiculo_PlacaAndEstado(
                        placaNormalizada,
                        EstadoPermiso.ACTIVO
                );

        List<PermisoResponse> respuestas = permisos.stream()
                .map(permiso -> PermisoResponse.desde(permiso, hoy))
                .toList();

        boolean vigente = permisos.stream()
                .anyMatch(permiso -> permiso.estaVigenteEn(hoy));

        log.info("Consulta de permisos finalizada. Placa: {}, Permisos activos encontrados: {}, Tiene permiso vigente: {}",
                placaNormalizada, permisos.size(), vigente);

        return new VigenciaPermisoResponse(
                placaNormalizada,
                hoy,
                vigente,
                respuestas
        );
    }


    /**
     * Revoca un permiso sin eliminarlo de la base de datos.
     */
    public PermisoResponse revocar(Long id) {

        log.info("Iniciando revocación de permiso. ID: {}", id);

        PermisoEntity permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un permiso con el ID: " + id
                ));

        // La operación es idempotente.
        if (permiso.getEstado() == EstadoPermiso.REVOCADO) {
            log.info("El permiso ID {} ya estaba revocado. No se requiere modificar su estado.", id);

            return PermisoResponse.desde(permiso, LocalDate.now());
        }

        // No se permite revocar un permiso mientras tenga un ingreso activo.
        boolean tieneIngresoActivo = ingresoRepository.existsByPermiso_IdAndEstado(
                id,
                EstadoIngreso.ACTIVO
        );

        if (tieneIngresoActivo) {
            log.warn("Revocación rechazada: el permiso ID {} tiene un ingreso activo.", id);

            throw new ReglaNegocioException(
                    "No se puede revocar el permiso porque tiene un ingreso activo. "
                            + "Debe registrarse primero la salida del vehículo."
            );
        }

        permiso.revocar();

        log.info("Permiso revocado correctamente. ID: {}, Placa: {}",
                id, permiso.getVehiculo().getPlaca());

        return PermisoResponse.desde(permiso, LocalDate.now());
    }

}

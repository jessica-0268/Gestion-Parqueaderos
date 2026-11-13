package co.edu.autonoma.gestionParqueaderos.service;

import co.edu.autonoma.gestionParqueaderos.dto.request.IngresoRequest;
import co.edu.autonoma.gestionParqueaderos.dto.request.SalidaRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.IngresoResponse;
import co.edu.autonoma.gestionParqueaderos.exception.RecursoNoEncontradoException;
import co.edu.autonoma.gestionParqueaderos.exception.ReglaNegocioException;
import co.edu.autonoma.gestionParqueaderos.model.entity.IngresoEntity;
import co.edu.autonoma.gestionParqueaderos.model.entity.PermisoEntity;
import co.edu.autonoma.gestionParqueaderos.model.entity.UsuarioEntity;
import co.edu.autonoma.gestionParqueaderos.model.entity.ZonaEntity;
import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoIngreso;
import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoPermiso;
import co.edu.autonoma.gestionParqueaderos.model.enums.RolUsuario;
import co.edu.autonoma.gestionParqueaderos.repository.IngresoRepository;
import co.edu.autonoma.gestionParqueaderos.repository.PermisoRepository;
import co.edu.autonoma.gestionParqueaderos.repository.UsuarioRepository;
import co.edu.autonoma.gestionParqueaderos.repository.ZonaRepository;
import co.edu.autonoma.gestionParqueaderos.util.PlacaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
public class IngresoService {

    private final IngresoRepository ingresoRepository;
    private final PermisoRepository permisoRepository;
    private final ZonaRepository zonaRepository;
    private final UsuarioRepository usuarioRepository;

    public IngresoService(
            IngresoRepository ingresoRepository,
            PermisoRepository permisoRepository,
            ZonaRepository zonaRepository,
            UsuarioRepository usuarioRepository) {
        this.ingresoRepository = ingresoRepository;
        this.permisoRepository = permisoRepository;
        this.zonaRepository = zonaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra el ingreso de un vehículo a una zona de parqueo.
     */
    public IngresoResponse registrarIngreso(IngresoRequest request) {

        String placa = PlacaUtil.normalizar(request.placa());

        log.info("Iniciando registro de ingreso. Placa: {}, Zona ID: {}, Usuario de seguridad ID: {}",
                placa, request.zonaId(), request.registradoIngresoPorId());

        UsuarioEntity usuario = obtenerUsuarioSeguridad(
                request.registradoIngresoPorId()
        );

        ZonaEntity zona = zonaRepository.findById(request.zonaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una zona con el ID: " + request.zonaId()
                ));

        LocalDateTime fechaIngreso = LocalDateTime.now();
        LocalDate hoy = fechaIngreso.toLocalDate();

        PermisoEntity permiso = permisoRepository
                .findAllByVehiculo_PlacaAndZona_IdAndEstado(
                        placa,
                        zona.getId(),
                        EstadoPermiso.ACTIVO
                )
                .stream()
                .filter(p -> p.estaVigenteEn(hoy))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Ingreso rechazado: el vehículo {} no tiene permiso vigente para la zona {}",
                            placa, zona.getId());

                    return new ReglaNegocioException(
                            "El vehículo " + placa
                                    + " no tiene un permiso vigente para la zona "
                                    + zona.getNombre()
                    );
                });

        if (ingresoRepository.existsByPermiso_Vehiculo_PlacaAndEstado(
                placa,
                EstadoIngreso.ACTIVO)) {

            log.warn("Ingreso rechazado: el vehículo {} ya tiene un ingreso activo", placa);

            throw new ReglaNegocioException(
                    "El vehículo " + placa
                            + " ya tiene un ingreso activo"
            );
        }

        long ocupados = ingresoRepository.countByPermiso_Zona_IdAndEstado(
                zona.getId(),
                EstadoIngreso.ACTIVO
        );

        if (ocupados >= zona.getCapacidad()) {

            log.warn("Ingreso rechazado: zona {} sin capacidad disponible. Ocupados: {}, Capacidad: {}",
                    zona.getId(), ocupados, zona.getCapacidad());

            throw new ReglaNegocioException(
                    "La zona " + zona.getNombre()
                            + " ha alcanzado su capacidad máxima"
            );
        }

        IngresoEntity ingreso = new IngresoEntity(
                permiso,
                fechaIngreso,
                usuario
        );

        IngresoEntity guardado = ingresoRepository.save(ingreso);

        log.info("Ingreso registrado correctamente. ID: {}, Placa: {}, Zona ID: {}",
                guardado.getId(), placa, zona.getId());

        return IngresoResponse.desde(guardado);
    }

    /**
     * Registra la salida de un vehículo y cierra su ingreso activo.
     */
    public IngresoResponse registrarSalida(Long id, SalidaRequest request) {

        log.info("Iniciando registro de salida. Ingreso ID: {}, Usuario de seguridad ID: {}",
                id, request.registradoSalidaPorId());

        UsuarioEntity usuario = obtenerUsuarioSeguridad(
                request.registradoSalidaPorId()
        );

        IngresoEntity ingreso = ingresoRepository.findByIdAndEstado(
                id,
                EstadoIngreso.ACTIVO
        ).orElseThrow(() -> new RecursoNoEncontradoException(
                "No existe un ingreso activo con el ID: " + id
        ));

        ingreso.cerrar(LocalDateTime.now(), usuario);

        log.info("Salida registrada correctamente. Ingreso ID: {}, Placa: {}",
                ingreso.getId(),
                ingreso.getPermiso().getVehiculo().getPlaca());

        return IngresoResponse.desde(ingreso);
    }

    /**
     * Consulta un ingreso mediante su identificador.
     * Permite consultar tanto ingresos activos como cerrados.
     *
     * @param id identificador del ingreso.
     * @return información del ingreso encontrado.
     */
    @Transactional(readOnly = true)
    public IngresoResponse consultarPorId(Long id) {

        log.info("Consultando ingreso por ID: {}", id);

        IngresoEntity ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un ingreso con el ID: " + id
                ));

        log.info("Ingreso consultado correctamente. ID: {}, Placa: {}, Estado: {}",
                ingreso.getId(),
                ingreso.getPermiso().getVehiculo().getPlaca(),
                ingreso.getEstado());

        return IngresoResponse.desde(ingreso);
    }

    /**
     * Busca un usuario y comprueba que tenga el rol SEGURIDAD.
     */
    private UsuarioEntity obtenerUsuarioSeguridad(Long usuarioId) {

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un usuario con el ID: " + usuarioId
                ));

        if (usuario.getRol() != RolUsuario.SEGURIDAD) {

            log.warn("Operación rechazada: el usuario ID {} no tiene el rol SEGURIDAD",
                    usuarioId);

            throw new ReglaNegocioException(
                    "El usuario indicado no tiene el rol de SEGURIDAD"
            );
        }

        return usuario;
    }
}

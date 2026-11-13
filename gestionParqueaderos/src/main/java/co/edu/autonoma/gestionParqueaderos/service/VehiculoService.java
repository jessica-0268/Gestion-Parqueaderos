package co.edu.autonoma.gestionParqueaderos.service;

import co.edu.autonoma.gestionParqueaderos.dto.request.VehiculoRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.VehiculoResponse;
import co.edu.autonoma.gestionParqueaderos.exception.RecursoNoEncontradoException;
import co.edu.autonoma.gestionParqueaderos.exception.ReglaNegocioException;
import co.edu.autonoma.gestionParqueaderos.model.entity.UsuarioEntity;
import co.edu.autonoma.gestionParqueaderos.model.entity.VehiculoEntity;
import co.edu.autonoma.gestionParqueaderos.model.enums.RolUsuario;
import co.edu.autonoma.gestionParqueaderos.repository.UsuarioRepository;
import co.edu.autonoma.gestionParqueaderos.repository.VehiculoRepository;
import co.edu.autonoma.gestionParqueaderos.util.PlacaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    public VehiculoService(
            VehiculoRepository vehiculoRepository,
            UsuarioRepository usuarioRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra un vehículo y lo asocia a un conductor si se proporciona.
     */
    public VehiculoResponse registrar(VehiculoRequest request) {

        String placa = PlacaUtil.normalizar(request.placa());

        log.info("Iniciando registro de vehículo. Placa: {}, Tipo: {}, Conductor ID: {}",
                placa, request.tipo(), request.conductorId());

        if (vehiculoRepository.findByPlaca(placa).isPresent()) {

            log.warn("Registro de vehículo rechazado: ya existe un vehículo con la placa {}",
                    placa);

            throw new ReglaNegocioException(
                    "Ya existe un vehículo registrado con la placa: " + placa
            );
        }

        UsuarioEntity conductor = null;

        if (request.conductorId() != null) {

            conductor = usuarioRepository.findById(request.conductorId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe un usuario con el ID: "
                                    + request.conductorId()
                    ));

            if (conductor.getRol() != RolUsuario.CONDUCTOR) {

                log.warn("Registro de vehículo rechazado: el usuario ID {} no tiene el rol CONDUCTOR",
                        request.conductorId());

                throw new ReglaNegocioException(
                        "El usuario indicado no tiene el rol de CONDUCTOR"
                );
            }
        }

        VehiculoEntity vehiculo = new VehiculoEntity(
                placa,
                request.tipo(),
                conductor
        );

        VehiculoEntity guardado = vehiculoRepository.save(vehiculo);

        log.info("Vehículo registrado correctamente. ID: {}, Placa: {}, Tipo: {}, Conductor ID: {}",
                guardado.getId(),
                placa,
                guardado.getTipo(),
                request.conductorId());

        return VehiculoResponse.desde(guardado);
    }

    /**
     * Consulta un vehículo mediante su placa.
     */
    @Transactional(readOnly = true)
    public VehiculoResponse consultarPorPlaca(String placa) {

        String placaNormalizada = PlacaUtil.normalizar(placa);

        log.info("Consultando vehículo por placa: {}", placaNormalizada);

        VehiculoEntity vehiculo = vehiculoRepository.findByPlaca(placaNormalizada)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un vehículo registrado con la placa: "
                                + placaNormalizada
                ));

        log.info("Vehículo encontrado correctamente. ID: {}, Placa: {}, Tipo: {}",
                vehiculo.getId(),
                vehiculo.getPlaca(),
                vehiculo.getTipo());

        return VehiculoResponse.desde(vehiculo);
    }
}

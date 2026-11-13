package co.edu.autonoma.gestionParqueaderos.controller;

import co.edu.autonoma.gestionParqueaderos.dto.request.VehiculoRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.VehiculoResponse;
import co.edu.autonoma.gestionParqueaderos.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    /**
     * Registra un vehículo y lo asocia a un conductor si se proporciona.
     *
     * @param request datos del vehículo que se desea registrar.
     * @return información del vehículo registrado.
     */
    @PostMapping
    public ResponseEntity<VehiculoResponse> registrar(
            @Valid @RequestBody VehiculoRequest request) {

        VehiculoResponse response = vehiculoService.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consulta un vehículo mediante su placa.
     *
     * @param placa matrícula del vehículo.
     * @return información del vehículo encontrado.
     */
    @GetMapping("/{placa}")
    public ResponseEntity<VehiculoResponse> consultarPorPlaca(
            @PathVariable String placa) {

        VehiculoResponse response = vehiculoService.consultarPorPlaca(placa);

        return ResponseEntity.ok(response);
    }
}

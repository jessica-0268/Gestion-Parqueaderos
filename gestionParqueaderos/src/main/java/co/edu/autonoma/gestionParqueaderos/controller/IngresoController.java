package co.edu.autonoma.gestionParqueaderos.controller;

import co.edu.autonoma.gestionParqueaderos.dto.request.IngresoRequest;
import co.edu.autonoma.gestionParqueaderos.dto.request.SalidaRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.IngresoResponse;
import co.edu.autonoma.gestionParqueaderos.service.IngresoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    private final IngresoService ingresoService;

    public IngresoController(IngresoService ingresoService) {
        this.ingresoService = ingresoService;
    }

    /**
     * Registra el ingreso de un vehículo al parqueadero.
     *
     * @param request datos necesarios para registrar el ingreso.
     * @return información del ingreso registrado.
     */
    @PostMapping
    public ResponseEntity<IngresoResponse> registrar(
            @Valid @RequestBody IngresoRequest request) {

        IngresoResponse response = ingresoService.registrarIngreso(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consulta un ingreso mediante su identificador.
     *
     * @param id identificador del ingreso.
     * @return información del ingreso encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IngresoResponse> consultarPorId(
            @PathVariable Long id) {

        IngresoResponse response = ingresoService.consultarPorId(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Registra la salida de un vehículo y cierra su ingreso activo.
     *
     * @param id identificador del ingreso que se desea cerrar.
     * @param request datos del usuario de seguridad que registra la salida.
     * @return información del ingreso actualizado.
     */
    @PatchMapping("/{id}/salida")
    public ResponseEntity<IngresoResponse> registrarSalida(
            @PathVariable Long id,
            @Valid @RequestBody SalidaRequest request) {

        IngresoResponse response = ingresoService.registrarSalida(id, request);

        return ResponseEntity.ok(response);
    }
}

package co.edu.autonoma.gestionParqueaderos.controller;

import co.edu.autonoma.gestionParqueaderos.dto.*;
import co.edu.autonoma.gestionParqueaderos.entity.*;
import co.edu.autonoma.gestionParqueaderos.service.ParqueaderoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor // Inyecta el service vía constructor implícito de Lombok
public class ParqueaderoController {

    private final ParqueaderoService parqueaderoService;

    // GET /api/parking/parqueadero/estado
    @GetMapping("/parqueadero/estado")
    public ResponseEntity<EstadoResponse> obtenerEstado() {
        return ResponseEntity.ok(null); // O la llamada a parqueaderoService.obtenerEstado()
    }

    // POST /api/parking/tickets/ingreso
    @PostMapping("/tickets/ingreso")
    public ResponseEntity<IngresoResponse> registrarIngreso(@RequestBody IngresoRequest request) {
        IngresoResponse response = parqueaderoService.registrarIngreso(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // POST /api/parking/tickets/salida
    @PostMapping("/tickets/salida")
    public ResponseEntity<SalidaResponse> registrarSalida(@RequestBody SalidaRequest request) {
        SalidaResponse response = parqueaderoService.registrarSalida(request);
        return ResponseEntity.ok(response);
    }

    // GET /api/parking/puestos
    @GetMapping("/puestos")
    public ResponseEntity<List<PuestoResponse>> listarPuestos() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    // GET /api/parking/tarifas
    @GetMapping("/tarifas")
    public ResponseEntity<List<TarifaEntity>> consultarTarifas() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    // PUT /api/parking/tarifas
    @PutMapping("/tarifas")
    public ResponseEntity<TarifaEntity> actualizarTarifa(@RequestBody TarifaRequest request) {
        return ResponseEntity.ok(null);
    }

    // GET /api/parking/vehiculos
    @GetMapping("/vehiculos")
    public ResponseEntity<List<VehiculoEntity>> listarVehiculos() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    // POST /api/parking/administrador/login
    @PostMapping("/administrador/login")
    public ResponseEntity<AdministradorEntity> loginAdmin(@RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(null);
    }
}
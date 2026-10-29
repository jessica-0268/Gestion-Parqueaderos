package co.edu.autonoma.gestionParqueaderos.controller;

import co.edu.autonoma.gestionParqueaderos.dto.*;
import co.edu.autonoma.gestionParqueaderos.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParqueaderoController {

    // GET /api/parqueadero/estado
    @GetMapping("/parqueadero/estado")
    public ResponseEntity<EstadoResponse> obtenerEstado() {
        return ResponseEntity.ok(new EstadoResponse());
    }

    // POST /api/tickets/ingreso
    @PostMapping("/tickets/ingreso")
    public ResponseEntity<IngresoResponse> registrarIngreso(@RequestBody co.edu.autonoma.gestionparqueaderos.dto.IngresoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new IngresoResponse(null, null, null, null));
    }

    // POST /api/tickets/salida
    @PostMapping("/tickets/salida")
    public ResponseEntity<SalidaResponse> registrarSalida(@RequestBody SalidaRequest request) {
        return ResponseEntity.ok(new SalidaResponse(null, null, null, null, null));
    }

    // GET /api/puestos
    @GetMapping("/puestos")
    public ResponseEntity<List<PuestoResponse>> listarPuestos() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    // GET /api/tarifas
    @GetMapping("/tarifas")
    public ResponseEntity<List<TarifaEntity>> consultarTarifas() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    // PUT /api/tarifas
    @PutMapping("/tarifas")
    public ResponseEntity<TarifaEntity> actualizarTarifa(@RequestBody TarifaRequest request) {
        return ResponseEntity.ok(new TarifaEntity());
    }

    // GET /api/vehiculos
    @GetMapping("/vehiculos")
    public ResponseEntity<List<VehiculoEntity>> listarVehiculos() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    // POST /api/administrador/login
    @PostMapping("/administrador/login")
    public ResponseEntity<AdministradorEntity> loginAdmin(@RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(new AdministradorEntity());
    }
}
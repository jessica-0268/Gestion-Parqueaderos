package co.edu.autonoma.gestionParqueaderos.controller;

import co.edu.autonoma.gestionParqueaderos.dto.request.ZonaRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.OcupacionResponse;
import co.edu.autonoma.gestionParqueaderos.dto.response.ZonaResponse;
import co.edu.autonoma.gestionParqueaderos.service.ZonaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    /**
     * Registra una nueva zona de parqueo.
     *
     * @param request datos de la zona que se desea registrar.
     * @return información de la zona registrada.
     */
    @PostMapping
    public ResponseEntity<ZonaResponse> registrar(
            @Valid @RequestBody ZonaRequest request) {

        ZonaResponse response = zonaService.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza la configuración de una zona de parqueo.
     *
     * @param id identificador de la zona.
     * @param request datos de la nueva configuración.
     * @return información actualizada de la zona.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ZonaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ZonaRequest request) {

        ZonaResponse response = zonaService.actualizar(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Consulta la ocupación actual de una zona de parqueo.
     *
     * @param id identificador de la zona.
     * @return información de capacidad, ocupación y disponibilidad.
     */
    @GetMapping("/{id}/ocupacion")
    public ResponseEntity<OcupacionResponse> consultarOcupacion(
            @PathVariable Long id) {

        OcupacionResponse response = zonaService.consultarOcupacion(id);

        return ResponseEntity.ok(response);
    }
}

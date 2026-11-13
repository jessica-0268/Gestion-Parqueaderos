package co.edu.autonoma.gestionParqueaderos.controller;

import co.edu.autonoma.gestionParqueaderos.dto.request.PermisoRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.PermisoResponse;
import co.edu.autonoma.gestionParqueaderos.dto.response.VigenciaPermisoResponse;
import co.edu.autonoma.gestionParqueaderos.service.PermisoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    /**
     * Registra un permiso de acceso para un vehículo en una zona.
     *
     * @param request datos del permiso que se desea registrar.
     * @return información del permiso registrado.
     */
    @PostMapping
    public ResponseEntity<PermisoResponse> registrar(
            @Valid @RequestBody PermisoRequest request) {

        PermisoResponse response = permisoService.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consulta los permisos activos de un vehículo y determina
     * si alguno se encuentra vigente en la fecha actual.
     *
     * @param placa matrícula del vehículo.
     * @return información de vigencia y permisos asociados.
     */
    @GetMapping("/{placa}")
    public ResponseEntity<VigenciaPermisoResponse> consultarPorPlaca(
            @PathVariable String placa) {

        VigenciaPermisoResponse response = permisoService.consultarPorPlaca(placa);

        return ResponseEntity.ok(response);
    }

    /**
     * Revoca un permiso de acceso.
     *
     * @param id identificador del permiso que se desea revocar.
     * @return información del permiso actualizado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<PermisoResponse> revocar(
            @PathVariable Long id) {

        PermisoResponse response = permisoService.revocar(id);

        return ResponseEntity.ok(response);
    }
}

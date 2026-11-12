package co.edu.autonoma.gestionParqueaderos.controller;

import co.edu.autonoma.gestionParqueaderos.dto.request.UsuarioRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.UsuarioResponse;
import co.edu.autonoma.gestionParqueaderos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request datos del usuario que se desea registrar.
     * @return información del usuario registrado.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> registrar(
            @Valid @RequestBody UsuarioRequest request) {

        UsuarioResponse response = usuarioService.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consulta un usuario mediante su identificador.
     *
     * @param id identificador del usuario.
     * @return información del usuario encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> consultarPorId(
            @PathVariable Long id) {

        UsuarioResponse response = usuarioService.consultarPorId(id);

        return ResponseEntity.ok(response);
    }
}

package co.edu.autonoma.gestionParqueaderos.service;

import co.edu.autonoma.gestionParqueaderos.dto.request.UsuarioRequest;
import co.edu.autonoma.gestionParqueaderos.dto.response.UsuarioResponse;
import co.edu.autonoma.gestionParqueaderos.exception.RecursoNoEncontradoException;
import co.edu.autonoma.gestionParqueaderos.exception.ReglaNegocioException;
import co.edu.autonoma.gestionParqueaderos.model.entity.UsuarioEntity;
import co.edu.autonoma.gestionParqueaderos.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra un usuario en el sistema.
     */
    public UsuarioResponse registrar(UsuarioRequest request) {

        String documento = request.documento().trim();

        log.info("Iniciando registro de usuario. Documento: {}, Rol: {}",
                documento, request.rol());

        if (usuarioRepository.existsByDocumento(documento)) {

            log.warn("Registro de usuario rechazado: ya existe un usuario con el documento {}",
                    documento);

            throw new ReglaNegocioException(
                    "Ya existe un usuario registrado con el documento: "
                            + documento
            );
        }

        UsuarioEntity usuario = new UsuarioEntity(
                request.nombre().trim(),
                documento,
                request.rol()
        );

        UsuarioEntity guardado = usuarioRepository.save(usuario);

        log.info("Usuario registrado correctamente. ID: {}, Documento: {}, Rol: {}",
                guardado.getId(), documento, guardado.getRol());

        return UsuarioResponse.desde(guardado);
    }

    /**
     * Consulta un usuario mediante su identificador.
     */
    @Transactional(readOnly = true)
    public UsuarioResponse consultarPorId(Long id) {

        log.info("Consultando usuario. ID: {}", id);

        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un usuario con el ID: " + id
                ));

        log.info("Usuario encontrado correctamente. ID: {}, Rol: {}",
                usuario.getId(), usuario.getRol());

        return UsuarioResponse.desde(usuario);
    }
}

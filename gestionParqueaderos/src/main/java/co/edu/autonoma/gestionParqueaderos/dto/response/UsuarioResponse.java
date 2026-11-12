package co.edu.autonoma.gestionParqueaderos.dto.response;

import co.edu.autonoma.gestionParqueaderos.model.entity.UsuarioEntity;
import co.edu.autonoma.gestionParqueaderos.model.enums.RolUsuario;

/**
 * Representa la información básica de un usuario del sistema,
 * incluyendo sus datos de identificación y rol asignado.
 */
public record UsuarioResponse(
        Long id,
        String nombre,
        String documento,
        RolUsuario rol
) {

    public static UsuarioResponse desde(UsuarioEntity usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getDocumento(),
                usuario.getRol()
        );
    }
}


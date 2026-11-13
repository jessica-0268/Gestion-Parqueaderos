package co.edu.autonoma.gestionParqueaderos.dto.request;

import co.edu.autonoma.gestionParqueaderos.model.enums.RolUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre admite máximo 100 caracteres")
        String nombre,

        @NotBlank(message = "El documento es obligatorio")
        @Size(max = 20, message = "El documento admite máximo 20 caracteres")
        String documento,

        @NotNull(message = "El rol es obligatorio")
        RolUsuario rol

) {}


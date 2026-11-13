package co.edu.autonoma.gestionParqueaderos.model.entity;

import co.edu.autonoma.gestionParqueaderos.model.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 20)
    private String documento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolUsuario rol;

    public UsuarioEntity(String nombre, String documento, RolUsuario rol) {
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
    }
}


package co.edu.autonoma.gestionParqueaderos.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administradores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdministradorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String contrasenna;

    // Constructor corregido: los parámetros coinciden con los atributos
    public AdministradorEntity(String usuario, String contrasenna) {
        this.usuario = usuario;
        this.contrasenna = contrasenna;
    }
}
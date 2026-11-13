package co.edu.autonoma.gestionParqueaderos.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "zonas")
@Getter
@Check(constraints = "capacidad > 0")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ZonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private int capacidad;

    public ZonaEntity(String nombre, int capacidad) {
        this.nombre = nombre.trim();
        this.capacidad = capacidad;
    }

    public void reemplazarConfiguracion(String nombre, int capacidad) {
        this.nombre = nombre.trim();
        this.capacidad = capacidad;
    }
}


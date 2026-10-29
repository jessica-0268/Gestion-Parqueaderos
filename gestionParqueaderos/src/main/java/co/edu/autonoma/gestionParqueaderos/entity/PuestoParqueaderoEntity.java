package co.edu.autonoma.gestionParqueaderos.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "puestos_parqueadero")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PuestoParqueaderoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String numeroPuesto;

    @Column(nullable = false)
    private String tipoVehiculo;

    @Column(nullable = false)
    private String estado; 

    public PuestoParqueaderoEntity(String numeroPuesto, String tipoVehiculo) {
        this.numeroPuesto = numeroPuesto;
        this.tipoVehiculo = tipoVehiculo;
        this.estado = "DISPONIBLE";
    }
}
package co.edu.autonoma.gestionParqueaderos.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarifas")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TarifaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoVehiculo;

    @Column(nullable = false)
    private Double valorPorHora;

    public TarifaEntity(String tipoVehiculo, Double valorPorHora) {
        this.tipoVehiculo = tipoVehiculo;
        this.valorPorHora = valorPorHora;
    }
}
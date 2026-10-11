package co.edu.autonoma.gestionParqueaderos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tarifas")
public class TarifaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String tipoVehiculo;

    @Column(nullable = false)
    private Double valorPorHora;

    public TarifaEntity() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
    public Double getValorPorHora() { return valorPorHora; }
    public void setValorPorHora(Double valorPorHora) { this.valorPorHora = valorPorHora; }
}
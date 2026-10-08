package co.edu.autonoma.gestionParqueaderos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehiculos")
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String tipoVehiculo; // Por ejemplo: CARRO, MOTO

    @Column(nullable = false)
    private LocalDateTime horaIngreso;


    public VehiculoEntity() {
    }

    public VehiculoEntity(String placa, String tipoVehiculo, LocalDateTime horaIngreso) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.horaIngreso = horaIngreso;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }
}

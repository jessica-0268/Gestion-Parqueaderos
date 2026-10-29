package co.edu.autonoma.gestionParqueaderos.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehiculos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "hora_ingreso", nullable = false)
    private LocalDateTime horaIngreso;

    public VehiculoEntity(String placa, String tipo) {
        this.placa = placa;
        this.tipo = tipo;
        this.horaIngreso = LocalDateTime.now();
    }
}
package co.edu.autonoma.gestionParqueaderos.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placaVehiculo;

    @Column(nullable = false)
    private String numeroPuesto;

    @Column(nullable = false)
    private LocalDateTime horaIngreso;

    private LocalDateTime horaSalida;
    private Double totalPagar;

    @Column(nullable = false)
    private String estado; // ACTIVO, PAGADO

    public TicketEntity(String placaVehiculo, String numeroPuesto) {
        this.placaVehiculo = placaVehiculo;
        this.numeroPuesto = numeroPuesto;
        this.horaIngreso = LocalDateTime.now();
        this.estado = "ACTIVO";
    }
}
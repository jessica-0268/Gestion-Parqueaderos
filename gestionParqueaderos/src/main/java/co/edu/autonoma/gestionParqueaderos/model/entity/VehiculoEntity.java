package co.edu.autonoma.gestionParqueaderos.model.entity;

import co.edu.autonoma.gestionParqueaderos.model.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehiculos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoVehiculo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id")
    private UsuarioEntity conductor;

    public VehiculoEntity(String placa, TipoVehiculo tipo, UsuarioEntity conductor) {
        this.placa = placa.trim().toUpperCase();
        this.tipo = tipo;
        this.conductor = conductor;
    }
}

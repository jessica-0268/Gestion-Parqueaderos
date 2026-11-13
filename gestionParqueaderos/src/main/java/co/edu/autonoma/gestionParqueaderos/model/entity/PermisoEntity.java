package co.edu.autonoma.gestionParqueaderos.model.entity;

import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoPermiso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "permisos")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PermisoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private VehiculoEntity vehiculo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zona_id", nullable = false)
    private ZonaEntity zona;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPermiso estado;

    public PermisoEntity(VehiculoEntity vehiculo, ZonaEntity zona, LocalDate fechaInicio, LocalDate fechaFin) {
        this.vehiculo = vehiculo;
        this.zona = zona;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoPermiso.ACTIVO;
    }

    /**
     * RN-01: Comprueba si el permiso está activo y vigente para una fecha determinada
     **/
    public boolean estaVigenteEn(LocalDate fecha) {
        return estado == EstadoPermiso.ACTIVO && !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
    }

    /** Revocacion logica del permiso */
    public void revocar() {
        this.estado = EstadoPermiso.REVOCADO;
    }
}


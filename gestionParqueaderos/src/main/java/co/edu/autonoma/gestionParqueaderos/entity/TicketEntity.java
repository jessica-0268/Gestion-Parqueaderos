package co.edu.autonoma.gestionParqueaderos.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime horaIngreso;

    private LocalDateTime horaSalida;

    private Double costoTotal;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private VehiculoEntity vehiculo;

    @ManyToOne
    @JoinColumn(name = "puesto_id", nullable = false)
    private PuestoParqueaderoEntity puestoParqueadero;

    @ManyToOne
    @JoinColumn(name = "tarifa_id", nullable = false)
    private TarifaEntity tarifa;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private AdministradorEntity administrador;

    public TicketEntity() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(LocalDateTime horaIngreso) { this.horaIngreso = horaIngreso; }
    public LocalDateTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalDateTime horaSalida) { this.horaSalida = horaSalida; }
    public Double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(Double costoTotal) { this.costoTotal = costoTotal; }
    public VehiculoEntity getVehiculo() { return vehiculo; }
    public void setVehiculo(VehiculoEntity vehiculo) { this.vehiculo = vehiculo; }
    public PuestoParqueaderoEntity getPuestoParqueadero() { return puestoParqueadero; }
    public void setPuestoParqueadero(PuestoParqueaderoEntity puestoParqueadero) { this.puestoParqueadero = puestoParqueadero; }
    public TarifaEntity getTarifa() { return tarifa; }
    public void setTarifa(TarifaEntity tarifa) { this.tarifa = tarifa; }
    public AdministradorEntity getAdministrador() { return administrador; }
    public void setAdministrador(AdministradorEntity administrador) { this.administrador = administrador; }
}
package co.edu.autonoma.gestionParqueaderos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "puestos_parqueadero")
public class PuestoParqueaderoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroPuesto;

    @Column(nullable = false)
    private Boolean disponible = true;

    public PuestoParqueaderoEntity() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNumeroPuesto() { return numeroPuesto; }
    public void setNumeroPuesto(String numeroPuesto) { this.numeroPuesto = numeroPuesto; }
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
}
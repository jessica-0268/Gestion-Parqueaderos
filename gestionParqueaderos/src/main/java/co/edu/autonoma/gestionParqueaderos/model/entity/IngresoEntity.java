package co.edu.autonoma.gestionParqueaderos.model.entity;

import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoIngreso;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ingresos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngresoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permiso_id", nullable = false)
    private PermisoEntity permiso;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoIngreso estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registrado_ingreso_por_id", nullable = false)
    private UsuarioEntity registradoIngresoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrado_salida_por_id")
    private UsuarioEntity registradoSalidaPor;

    public IngresoEntity(PermisoEntity permiso, LocalDateTime fechaIngreso, UsuarioEntity registradoIngresoPor) {
        this.permiso = permiso;
        this.fechaIngreso = fechaIngreso;
        this.registradoIngresoPor = registradoIngresoPor;
        this.estado = EstadoIngreso.ACTIVO;
    }

    /** RN-05: Cierra el registro de ingreso. */
    public void cerrar(LocalDateTime fechaSalida, UsuarioEntity registradoSalidaPor) {
        this.fechaSalida = fechaSalida;
        this.registradoSalidaPor = registradoSalidaPor;
        this.estado = EstadoIngreso.CERRADO;
    }
}


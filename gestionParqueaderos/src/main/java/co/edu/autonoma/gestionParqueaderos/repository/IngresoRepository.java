package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoIngreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngresoRepository extends JpaRepository<IngresoEntity, Long> {

    /** RN-03: Verifica si un vehículo tiene un ingreso activo. */
    boolean existsByPermiso_Vehiculo_PlacaAndEstado(String placa, EstadoIngreso estado);

    /** RN-04, RN-06: Cuenta los vehículos que permanecen dentro de una zona. */
    long countByPermiso_Zona_IdAndEstado(Long zonaId, EstadoIngreso estado);

    /** RN-05: Busca un ingreso por su identificador y estado. */
    Optional<IngresoEntity> findByIdAndEstado(Long id, EstadoIngreso estado);

    /** Verifica si un permiso tiene un ingreso activo. */
    boolean existsByPermiso_IdAndEstado(Long permisoId, EstadoIngreso estado);

}


package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.model.entity.PermisoEntity;
import co.edu.autonoma.gestionParqueaderos.model.enums.EstadoPermiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermisoRepository extends JpaRepository<PermisoEntity, Long> {

    /** RN-01: Consulta los permisos de un vehículo por su placa. */
    List<PermisoEntity> findAllByVehiculo_PlacaAndEstado(String placa, EstadoPermiso estado);

    /** RN-02: Consulta los permisos de un vehículo para una zona específica. */
    List<PermisoEntity> findAllByVehiculo_PlacaAndZona_IdAndEstado(String placa, Long zonaId, EstadoPermiso estado);

}


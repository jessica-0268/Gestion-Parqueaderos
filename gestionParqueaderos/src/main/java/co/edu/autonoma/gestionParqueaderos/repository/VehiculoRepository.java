package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.model.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {

    /** Consulta un vehículo por su placa. */
    Optional<VehiculoEntity> findByPlaca(String placa);

}
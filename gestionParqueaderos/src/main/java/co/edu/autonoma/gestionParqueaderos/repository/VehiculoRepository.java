package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Integer> {

    Optional<VehiculoEntity> findByPlaca(String placa);

    Integer countByTipoVehiculo(String tipoVehiculo);
}

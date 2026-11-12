package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.model.entity.PuestoParqueaderoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuestoParqueaderoRepository extends JpaRepository<PuestoParqueaderoEntity, Integer> {
}
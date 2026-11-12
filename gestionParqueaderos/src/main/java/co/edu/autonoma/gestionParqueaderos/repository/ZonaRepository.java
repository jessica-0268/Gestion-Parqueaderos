package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.model.entity.ZonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZonaRepository extends JpaRepository<ZonaEntity, Long> {

    /** RN-04: Actualiza la configuración de una zona "save()". */
    /** RN-06: Consultar TODAS las zonas registradas "findAll()". */
    /** RN-06: Consultar UNA zona registrada por ID "findById()". */

}


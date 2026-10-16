package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.entity.PuestoParqueaderoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PuestoParqueaderoRepository extends JpaRepository<PuestoParqueaderoEntity, Integer> {

    Optional<PuestoParqueaderoEntity> findByNumeroPuesto(String numeroPuesto);
    List<PuestoParqueaderoEntity> findByOcupado(boolean ocupado);

    @Query("SELECT p FROM PuestoParqueaderoEntity p WHERE p.ocupado = false AND p.tipoPermitido = :tipo")
    List<PuestoParqueaderoEntity> buscarDisponiblesPorTipo(@Param("tipo") String tipo);

    @Query("SELECT COUNT(p) FROM PuestoParqueaderoEntity p WHERE p.ocupado = false AND p.tipoPermitido = :tipo")
    long contarPuestosLibresPorTipo(@Param("tipo") String tipo);
}
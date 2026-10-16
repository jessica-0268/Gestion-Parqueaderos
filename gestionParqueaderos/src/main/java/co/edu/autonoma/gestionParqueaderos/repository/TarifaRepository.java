package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.entity.TarifaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface TarifaRepository extends JpaRepository<TarifaEntity, Integer> {

    Optional<TarifaEntity> findByTipoVehiculo(String tipoVehiculo);

    @Query("SELECT t FROM TarifaEntity t WHERE UPPER(t.tipoVehiculo) = UPPER(:tipo)")
    Optional<TarifaEntity> buscarPorTipoIgnorandoMayusculas(@Param("tipo") String tipo);

    @Query("SELECT t.valorHora FROM TarifaEntity t WHERE t.tipoVehiculo = :tipo")
    Optional<BigDecimal> obtenerPrecioPorTipo(@Param("tipo") String tipo);
}

package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

    @Query("SELECT t FROM TicketEntity t WHERE t.fechaIngreso > :desde")
    List<TicketEntity> buscarTicketsRecientes(@Param("desde") LocalDateTime desde);
}

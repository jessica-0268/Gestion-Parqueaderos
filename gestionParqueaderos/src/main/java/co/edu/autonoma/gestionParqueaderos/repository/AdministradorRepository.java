package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.entity.AdministradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<AdministradorEntity, Integer> {

    Optional<AdministradorEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT a FROM AdministradorEntity a WHERE a.email = :email AND a.password = :password")
    Optional<AdministradorEntity> validarCredenciales(@Param("email") String email, @Param("password") String password);
}
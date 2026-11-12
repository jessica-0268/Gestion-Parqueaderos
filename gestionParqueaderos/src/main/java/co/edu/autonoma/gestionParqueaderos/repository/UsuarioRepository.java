package co.edu.autonoma.gestionParqueaderos.repository;

import co.edu.autonoma.gestionParqueaderos.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    /** findById():	Recupera un usuario por su ID. */
    /** findAll(): Consulta TODOS los usuarios registrados. */
    /** save(): Registrar o actualizar usuarios. */
    /** deleteById(): Elimina un usuario por ID. */

    boolean existsByDocumento(String documento);

}


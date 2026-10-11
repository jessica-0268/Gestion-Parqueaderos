package co.edu.autonoma.gestionParqueaderos;

import co.edu.autonoma.gestionParqueaderos.repository.VehiculoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PersistenciaCheck implements CommandLineRunner {

    private final VehiculoRepository repository;

    public PersistenciaCheck(VehiculoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        System.out.println(">>> Conexión exitosa. Total en BD: " + repository.count());
    }
}

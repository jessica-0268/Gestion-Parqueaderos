package co.edu.autonoma.gestionParqueaderos.service;
import org.springframework.stereotype.Service;
import co.edu.autonoma.gestionParqueaderos.dto.EstadoResponse;

@Service
public class EstadoService {

    public EstadoResponse consultarEstado() {
        return new EstadoResponse(
                "Gestion-parqueaderos",
                "disponible"
        );
    }
}


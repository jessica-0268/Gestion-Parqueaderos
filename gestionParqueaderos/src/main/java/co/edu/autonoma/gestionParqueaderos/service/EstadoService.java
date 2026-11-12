package co.edu.autonoma.gestionParqueaderos.service;

import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    public EstadoResponse consultarEstado() {
        return new EstadoResponse();
    }
}


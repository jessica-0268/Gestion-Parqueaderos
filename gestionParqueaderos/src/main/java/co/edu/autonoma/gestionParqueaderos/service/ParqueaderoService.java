package co.edu.autonoma.gestionParqueaderos.service;

import co.edu.autonoma.gestionParqueaderos.model.entity.TicketEntity;
import co.edu.autonoma.gestionParqueaderos.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParqueaderoService {

    private final TicketRepository ticketRepository;

    public IngresoResponse registrarIngreso(IngresoRequest request) {
        log.info("Procesando ingreso de vehiculo con placa: {}", request.placa());

        TicketEntity nuevoTicket = new TicketEntity(
                request.placa(),
                "A1"
        );

        TicketEntity ticketGuardado = ticketRepository.save(nuevoTicket);

        return new IngresoResponse(
                ticketGuardado.getId(),
                ticketGuardado.getPlacaVehiculo(),
                ticketGuardado.getNumeroPuesto(),
                ticketGuardado.getHoraIngreso()
        );
    }


    public SalidaResponse registrarSalida(SalidaRequest request) {
        log.info("Procesando salida para el ticket ID: {}", request.ticketId());

        return new SalidaResponse(
                request.ticketId(),
                "ABC123",
                LocalDateTime.now(),
                LocalDateTime.now(),
                5000.0
        );
    }
    }

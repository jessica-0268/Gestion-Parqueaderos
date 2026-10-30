package co.edu.autonoma.gestionParqueaderos.service;

import co.edu.autonoma.gestionParqueaderos.dto.IngresoRequest;
import co.edu.autonoma.gestionParqueaderos.dto.IngresoResponse;
import co.edu.autonoma.gestionParqueaderos.dto.SalidaRequest;
import co.edu.autonoma.gestionParqueaderos.dto.SalidaResponse;
import co.edu.autonoma.gestionParqueaderos.entity.TicketEntity;
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
                request.ticketId(),   // 1. idTicket
                "ABC123",            // 2. placa (fija por ahora)
                LocalDateTime.now(),  // 3. fechaIngreso
                LocalDateTime.now(),  // 4. fechaSalida
                5000.0                // 5. totalPagar
        );
    }
    }

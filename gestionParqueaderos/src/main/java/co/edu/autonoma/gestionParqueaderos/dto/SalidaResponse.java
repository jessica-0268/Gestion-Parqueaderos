package co.edu.autonoma.gestionParqueaderos.dto;

import java.time.LocalDateTime;

public class SalidaResponse {
    private Long idTicket;
    private String placa;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private Double totalPagar;

    public SalidaResponse(Long idTicket, String placa, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, Double totalPagar) {
        this.idTicket = idTicket;
        this.placa = placa;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.totalPagar = totalPagar;
    }

    public Long getIdTicket() { return idTicket; }
    public String getPlaca() { return placa; }
    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public Double getTotalPagar() { return totalPagar; }
}
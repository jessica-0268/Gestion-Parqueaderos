package co.edu.autonoma.gestionParqueaderos.dto;

import java.time.LocalDateTime;

public class IngresoResponse {
    private Long idTicket;
    private String placa;
    private String codigoPuesto;
    private LocalDateTime fechaIngreso;

    public IngresoResponse(Long idTicket, String placa, String codigoPuesto, LocalDateTime fechaIngreso) {
        this.idTicket = idTicket;
        this.placa = placa;
        this.codigoPuesto = codigoPuesto;
        this.fechaIngreso = fechaIngreso;
    }

    public Long getIdTicket() { return idTicket; }
    public String getPlaca() { return placa; }
    public String getCodigoPuesto() { return codigoPuesto; }
    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
}
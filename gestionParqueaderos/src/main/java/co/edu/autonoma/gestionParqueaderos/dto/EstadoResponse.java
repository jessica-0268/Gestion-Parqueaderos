package co.edu.autonoma.gestionParqueaderos.dto;

public class EstadoResponse {
    private int totalPuestos;
    private int puestosOcupados;
    private int puestosDisponibles;
    private String mensaje;

    public EstadoResponse() {}

    public EstadoResponse(int totalPuestos, int puestosOcupados, int puestosDisponibles, String mensaje) {
        this.totalPuestos = totalPuestos;
        this.puestosOcupados = puestosOcupados;
        this.puestosDisponibles = puestosDisponibles;
        this.mensaje = mensaje;
    }

    public int getTotalPuestos() { return totalPuestos; }
    public void setTotalPuestos(int totalPuestos) { this.totalPuestos = totalPuestos; }

    public int getPuestosOcupados() { return puestosOcupados; }
    public void setPuestosOcupados(int puestosOcupados) { this.puestosOcupados = puestosOcupados; }

    public int getPuestosDisponibles() { return puestosDisponibles; }
    public void setPuestosDisponibles(int puestosDisponibles) { this.puestosDisponibles = puestosDisponibles; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
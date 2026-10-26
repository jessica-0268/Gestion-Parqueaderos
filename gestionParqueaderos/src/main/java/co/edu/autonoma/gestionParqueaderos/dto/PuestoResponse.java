package co.edu.autonoma.gestionParqueaderos.dto;

public class PuestoResponse {
    private Long id;
    private String codigo;
    private boolean ocupado;

    public PuestoResponse(Long id, String codigo, boolean ocupado) {
        this.id = id;
        this.codigo = codigo;
        this.ocupado = ocupado;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public boolean isOcupado() { return ocupado; }
}
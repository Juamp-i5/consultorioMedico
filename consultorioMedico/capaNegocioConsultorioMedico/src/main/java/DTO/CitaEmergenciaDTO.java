package DTO;

import java.time.LocalDateTime;

/**
 * @author
 */
public class CitaEmergenciaDTO {
    private LocalDateTime fechaHora;
    private String nombreCompletoMedico;
    private String folio;

    public CitaEmergenciaDTO(LocalDateTime fechaHora, String nombreCompletoMedico, String folio) {
        this.fechaHora = fechaHora;
        this.nombreCompletoMedico = nombreCompletoMedico;
        this.folio = folio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombreCompletoMedico() {
        return nombreCompletoMedico;
    }

    public void setNombreCompletoMedico(String nombreCompletoMedico) {
        this.nombreCompletoMedico = nombreCompletoMedico;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
    
    
}

package DTO;

import java.util.Date;

/**
 *
 * @author janot
 */
public class CitaDTO {
    private String tipo;
    private String folio;
    private Date fechaHora;
    private String estado;
    private int idPaciente;
    private int idMedico;

    public CitaDTO(String tipo, String folio, Date fechaHora, String estado, int idPaciente, int idMedico) {
        this.tipo = tipo;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }
    
}

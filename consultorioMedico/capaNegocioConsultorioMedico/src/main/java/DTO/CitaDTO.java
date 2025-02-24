package DTO;

import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author janot
 */
public class CitaDTO {
    private String tipo;
    private String folio;
    private Date fecha;
    private LocalTime hora;
    private String estado;
    private int idPaciente;
    private int idMedico;

    public CitaDTO(String tipo, String folio, Date fecha, LocalTime hora, String estado, int idPaciente, int idMedico) {
        this.tipo = tipo;
        this.folio = folio;
        this.fecha = fecha;
        this.hora = hora;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFechaHora(Date fecha) {
        this.fecha = fecha;
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

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    
}

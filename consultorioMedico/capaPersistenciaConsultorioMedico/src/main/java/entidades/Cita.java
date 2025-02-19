/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Jp
 */
public class Cita {

    private int idCita;
    private String tipo;
    private String folio;
    private String fechaHora;
    private String estado;
    private int idPaciente;
    private int idMedico;

    public Cita() {
    }

    public Cita(int idCita, String tipo, String folio, String fechaHora, String estado, int idPaciente, int idMedico) {
        this.idCita = idCita;
        this.tipo = tipo;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
    }

    public Cita(String tipo, String folio, String fechaHora, String estado, int idPaciente, int idMedico) {
        this.tipo = tipo;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
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

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
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

    @Override
    public String toString() {
        return "Cita{" + "idCita=" + idCita + ", tipo=" + tipo + ", folio=" + folio + ", fechaHora=" + fechaHora + ", estado=" + estado + ", idPaciente=" + idPaciente + ", idMedico=" + idMedico + '}';
    }

}

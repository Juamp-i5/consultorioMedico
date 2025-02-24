/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;


import java.time.LocalDateTime;

/**
 * Representa una cita médica en el sistema. Contiene información sobre el tipo
 * de cita, el folio, la fecha y hora, el estado de la cita y los
 * identificadores del paciente y del médico.
 *
 * @author Jp
 */
public class Cita {

    /**
     * Identificador único de la cita.
     */
    private int idCita;

    /**
     * Tipo de la cita (ej. "Consulta general", "Especialidad").
     */
    private String tipo;

    /**
     * Folio único de la cita.
     */
    private String folio;

    /**
     * Fecha y hora programada para la cita.
     */
    private LocalDateTime fechaHora;

    /**
     * Estado actual de la cita (ej. "Pendiente", "Cancelada", "Finalizada").
     */
    private String estado;

    /**
     * Identificador del paciente asociado a la cita.
     */
    private int idPaciente;

    /**
     * Identificador del médico que atenderá la cita.
     */
    private int idMedico;

    /**
     * Constructor por defecto.
     */
    public Cita() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param idCita Identificador único de la cita.
     * @param tipo Tipo de la cita.
     * @param folio Folio único de la cita.
     * @param fechaHora Fecha y hora programada.
     * @param estado Estado actual de la cita.
     * @param idPaciente Identificador del paciente asociado.
     * @param idMedico Identificador del médico que atenderá la cita.
     */
    public Cita(int idCita, String tipo, String folio, LocalDateTime fechaHora, String estado, int idPaciente, int idMedico) {
        this.idCita = idCita;
        this.tipo = tipo;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
    }

    /**
     * Constructor sin ID de cita.
     *
     * @param tipo Tipo de la cita.
     * @param folio Folio único de la cita.
     * @param fechaHora Fecha y hora programada.
     * @param estado Estado actual de la cita.
     * @param idPaciente Identificador del paciente asociado.
     * @param idMedico Identificador del médico que atenderá la cita.
     */
    public Cita(String tipo, String folio, LocalDateTime fechaHora, String estado, int idPaciente, int idMedico) {
        this.tipo = tipo;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
    }

    /**
     * Obtiene el identificador de la cita.
     *
     * @return ID de la cita.
     */
    public int getIdCita() {
        return idCita;
    }

    /**
     * Establece el identificador de la cita.
     *
     * @param idCita Nuevo ID de la cita.
     */
    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    /**
     * Obtiene el tipo de la cita.
     *
     * @return Tipo de la cita.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la cita.
     *
     * @param tipo Nuevo tipo de la cita.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el folio de la cita.
     *
     * @return Folio de la cita.
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Establece el folio de la cita.
     *
     * @param folio Nuevo folio de la cita.
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * Obtiene la fecha y hora programada de la cita.
     *
     * @return Fecha y hora de la cita.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de la cita.
     *
     * @param fechaHora Nueva fecha y hora de la cita.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene el estado de la cita.
     *
     * @return Estado de la cita.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la cita.
     *
     * @param estado Nuevo estado de la cita.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el identificador del paciente asociado a la cita.
     *
     * @return ID del paciente.
     */
    public int getIdPaciente() {
        return idPaciente;
    }

    /**
     * Establece el identificador del paciente asociado a la cita.
     *
     * @param idPaciente Nuevo ID del paciente.
     */
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * Obtiene el identificador del médico que atenderá la cita.
     *
     * @return ID del médico.
     */
    public int getIdMedico() {
        return idMedico;
    }

    /**
     * Establece el identificador del médico que atenderá la cita.
     *
     * @param idMedico Nuevo ID del médico.
     */
    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    /**
     * Devuelve una representación en cadena del objeto Cita.
     *
     * @return Cadena con los datos de la cita.
     */
    @Override
    public String toString() {
        return "Cita{" + "idCita=" + idCita + ", tipo=" + tipo + ", folio=" + folio + ", fechaHora=" + fechaHora + ", estado=" + estado + ", idPaciente=" + idPaciente + ", idMedico=" + idMedico + '}';
    }

}

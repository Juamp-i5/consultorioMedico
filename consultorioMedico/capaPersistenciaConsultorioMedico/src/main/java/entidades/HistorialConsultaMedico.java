package entidades;

import java.time.LocalDateTime;

/**
 * Representa el historial de consultas médicas de un paciente.
 * Contiene información sobre la fecha y hora de la consulta, el tipo y estado de la consulta,
 * el paciente involucrado, el diagnóstico y el tratamiento.
 * 
 * @author Jp
 */
public class HistorialConsultaMedico {
    private LocalDateTime fechaHora;
    private String tipoConsulta;
    private String estadoConsulta;
    private String nombrePaciente;
    private String apellidoPaternoPaciente;
    private String apellidoMaternoPaciente;
    private String diagnostico;
    private String tratamiento;
    private int idPaciente;

    /**
     * Constructor para inicializar un historial de consulta médica.
     * 
     * @param fechaHora Fecha y hora de la consulta.
     * @param tipoConsulta Tipo de consulta realizada.
     * @param estadoConsulta Estado actual de la consulta.
     * @param nombrePaciente Nombre del paciente.
     * @param apellidoPaternoPaciente Apellido paterno del paciente.
     * @param apellidoMaternoPaciente Apellido materno del paciente.
     * @param diagnostico Diagnóstico determinado por el médico.
     * @param tratamiento Tratamiento indicado para el paciente.
     * @param idPaciente Identificador único del paciente.
     */
    public HistorialConsultaMedico(LocalDateTime fechaHora, String tipoConsulta, String estadoConsulta, String nombrePaciente, String apellidoPaternoPaciente, String apellidoMaternoPaciente, String diagnostico, String tratamiento, int idPaciente) {
        this.fechaHora = fechaHora;
        this.tipoConsulta = tipoConsulta;
        this.estadoConsulta = estadoConsulta;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaternoPaciente = apellidoPaternoPaciente;
        this.apellidoMaternoPaciente = apellidoMaternoPaciente;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.idPaciente = idPaciente;
    }

    /**
     * @return la fecha y hora de la consulta.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora establece la fecha y hora de la consulta.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return el tipo de consulta.
     */
    public String getTipoConsulta() {
        return tipoConsulta;
    }

    /**
     * @param tipoConsulta establece el tipo de consulta.
     */
    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    /**
     * @return el estado de la consulta.
     */
    public String getEstadoConsulta() {
        return estadoConsulta;
    }

    /**
     * @param estadoConsulta establece el estado de la consulta.
     */
    public void setEstadoConsulta(String estadoConsulta) {
        this.estadoConsulta = estadoConsulta;
    }

    /**
     * @return el nombre del paciente.
     */
    public String getNombrePaciente() {
        return nombrePaciente;
    }

    /**
     * @param nombrePaciente establece el nombre del paciente.
     */
    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    /**
     * @return el apellido paterno del paciente.
     */
    public String getApellidoPaternoPaciente() {
        return apellidoPaternoPaciente;
    }

    /**
     * @param apellidoPaternoPaciente establece el apellido paterno del paciente.
     */
    public void setApellidoPaternoPaciente(String apellidoPaternoPaciente) {
        this.apellidoPaternoPaciente = apellidoPaternoPaciente;
    }

    /**
     * @return el apellido materno del paciente.
     */
    public String getApellidoMaternoPaciente() {
        return apellidoMaternoPaciente;
    }

    /**
     * @param apellidoMaternoPaciente establece el apellido materno del paciente.
     */
    public void setApellidoMaternoPaciente(String apellidoMaternoPaciente) {
        this.apellidoMaternoPaciente = apellidoMaternoPaciente;
    }

    /**
     * @return el diagnóstico de la consulta.
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * @param diagnostico establece el diagnóstico de la consulta.
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * @return el tratamiento recomendado.
     */
    public String getTratamiento() {
        return tratamiento;
    }

    /**
     * @param tratamiento establece el tratamiento recomendado.
     */
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    /**
     * @return el identificador del paciente.
     */
    public int getIdPaciente() {
        return idPaciente;
    }

    /**
     * @param idPaciente establece el identificador del paciente.
     */
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
}

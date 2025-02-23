/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 * Representa una consulta médica en el sistema. Contiene información sobre el
 * diagnóstico, tratamiento, notas médicas, estado de la consulta y la cita
 * asociada.
 *
 * @author Jp
 */
public class Consulta {

    /**
     * Identificador único de la consulta.
     */
    private int idConsulta;

    /**
     * Diagnóstico determinado durante la consulta.
     */
    private String diagnostico;

    /**
     * Tratamiento recomendado para el paciente.
     */
    private String tratamiento;

    /**
     * Notas adicionales tomadas durante la consulta.
     */
    private String notasMedicas;

    /**
     * Estado actual de la consulta (ej. "Pendiente", "Finalizada").
     */
    private String estado;

    /**
     * Identificador de la cita a la que pertenece esta consulta.
     */
    private int idCita;

    /**
     * Constructor por defecto.
     */
    public Consulta() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param idConsulta Identificador de la consulta.
     * @param diagnostico Diagnóstico determinado en la consulta.
     * @param tratamiento Tratamiento recomendado.
     * @param notasMedicas Notas adicionales de la consulta.
     * @param estado Estado actual de la consulta.
     * @param idCita Identificador de la cita asociada.
     */
    public Consulta(int idConsulta, String diagnostico, String tratamiento, String notasMedicas, String estado, int idCita) {
        this.idConsulta = idConsulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.notasMedicas = notasMedicas;
        this.estado = estado;
        this.idCita = idCita;
    }

    /**
     * Constructor sin ID de consulta.
     *
     * @param diagnostico Diagnóstico determinado en la consulta.
     * @param tratamiento Tratamiento recomendado.
     * @param notasMedicas Notas adicionales de la consulta.
     * @param estado Estado actual de la consulta.
     * @param idCita Identificador de la cita asociada.
     */
    public Consulta(String diagnostico, String tratamiento, String notasMedicas, String estado, int idCita) {
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.notasMedicas = notasMedicas;
        this.estado = estado;
        this.idCita = idCita;
    }

    /**
     * Obtiene el identificador de la consulta.
     *
     * @return ID de la consulta.
     */
    public int getIdConsulta() {
        return idConsulta;
    }

    /**
     * Establece el identificador de la consulta.
     *
     * @param idConsulta Nuevo ID de la consulta.
     */
    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    /**
     * Obtiene el diagnóstico de la consulta.
     *
     * @return Diagnóstico de la consulta.
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * Establece el diagnóstico de la consulta.
     *
     * @param diagnostico Nuevo diagnóstico de la consulta.
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * Obtiene el tratamiento recomendado en la consulta.
     *
     * @return Tratamiento de la consulta.
     */
    public String getTratamiento() {
        return tratamiento;
    }

    /**
     * Establece el tratamiento recomendado en la consulta.
     *
     * @param tratamiento Nuevo tratamiento de la consulta.
     */
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    /**
     * Obtiene las notas médicas registradas en la consulta.
     *
     * @return Notas médicas de la consulta.
     */
    public String getNotasMedicas() {
        return notasMedicas;
    }

    /**
     * Establece las notas médicas de la consulta.
     *
     * @param notasMedicas Nuevas notas médicas de la consulta.
     */
    public void setNotasMedicas(String notasMedicas) {
        this.notasMedicas = notasMedicas;
    }

    /**
     * Obtiene el estado actual de la consulta.
     *
     * @return Estado de la consulta.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la consulta.
     *
     * @param estado Nuevo estado de la consulta.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el identificador de la cita asociada a la consulta.
     *
     * @return ID de la cita asociada.
     */
    public int getIdCita() {
        return idCita;
    }

    /**
     * Establece el identificador de la cita asociada a la consulta.
     *
     * @param idCita Nuevo ID de la cita asociada.
     */
    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    /**
     * Devuelve una representación en cadena del objeto Consulta.
     *
     * @return Cadena con los datos de la consulta.
     */
    @Override
    public String toString() {
        return "Consulta{" + "idConsulta=" + idConsulta + ", diagnostico=" + diagnostico + ", tratamiento=" + tratamiento + ", notasMedicas=" + notasMedicas + ", estado=" + estado + ", idCita=" + idCita + '}';
    }

}

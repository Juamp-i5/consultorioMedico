/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Jp
 */
public class Consulta {

    private int idConsulta;
    private String diagnostico;
    private String tratamiento;
    private String notasMedicas;
    private String estado;
    private int idCita;

    public Consulta() {
    }

    public Consulta(int idConsulta, String diagnostico, String tratamiento, String notasMedicas, String estado, int idCita) {
        this.idConsulta = idConsulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.notasMedicas = notasMedicas;
        this.estado = estado;
        this.idCita = idCita;
    }

    public Consulta(String diagnostico, String tratamiento, String notasMedicas, String estado, int idCita) {
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.notasMedicas = notasMedicas;
        this.estado = estado;
        this.idCita = idCita;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNotasMedicas() {
        return notasMedicas;
    }

    public void setNotasMedicas(String notasMedicas) {
        this.notasMedicas = notasMedicas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    @Override
    public String toString() {
        return "Consulta{" + "idConsulta=" + idConsulta + ", diagnostico=" + diagnostico + ", tratamiento=" + tratamiento + ", notasMedicas=" + notasMedicas + ", estado=" + estado + ", idCita=" + idCita + '}';
    }

}

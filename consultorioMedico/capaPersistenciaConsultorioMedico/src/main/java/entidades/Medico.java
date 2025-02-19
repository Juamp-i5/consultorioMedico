/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Jp
 */
public class Medico {

    private int idMedico;
    private String especialidad;
    private String cedulaProfesional;
    private String estado;

    public Medico() {
    }

    public Medico(int idMedico, String especialidad, String cedulaProfesional, String estado) {
        this.idMedico = idMedico;
        this.especialidad = especialidad;
        this.cedulaProfesional = cedulaProfesional;
        this.estado = estado;
    }

    public Medico(String especialidad, String cedulaProfesional, String estado) {
        this.especialidad = especialidad;
        this.cedulaProfesional = cedulaProfesional;
        this.estado = estado;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Medico{" + "idMedico=" + idMedico + ", especialidad=" + especialidad + ", cedulaProfesional=" + cedulaProfesional + ", estado=" + estado + '}';
    }

}
